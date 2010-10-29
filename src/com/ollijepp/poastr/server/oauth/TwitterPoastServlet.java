//========================================================================
//Copyright 2007-2009 David Yu dyuproject@gmail.com
//------------------------------------------------------------------------
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at 
//http://www.apache.org/licenses/LICENSE-2.0
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
//========================================================================
//Modified by Oliver Uvman, same license applies.

package com.ollijepp.poastr.server.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.dyuproject.oauth.Constants;
import com.dyuproject.oauth.Consumer;
import com.dyuproject.oauth.ConsumerContext;
import com.dyuproject.oauth.Endpoint;
import com.dyuproject.oauth.HttpAuthTransport;
import com.dyuproject.oauth.Token;
import com.dyuproject.oauth.TokenExchange;
import com.dyuproject.oauth.Transport;
import com.dyuproject.util.http.HttpConnector;
import com.dyuproject.util.http.UrlEncodedParameterMap;
import com.dyuproject.util.http.HttpConnector.Parameter;
import com.dyuproject.util.http.HttpConnector.Response;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * We assume that the request includes String values for the parameters
 * accountOwnerHashed and serviceProvider, this is to distinguish
 * different user's credentials from each other in the datastore.
 * 
 * Of course, a String message should include the new status. 
 */

@SuppressWarnings("serial")
@RemoteServiceRelativePath("twitterpoast")
public class TwitterPoastServlet extends HttpServlet
{
	private static final Logger log = Logger.getLogger(TwitterPoastServlet.class);
    static final String TWITTER_SERVICE_URL = "http://api.twitter.com/1/statuses/update.json";
    
    static final Consumer consumer = Consumer.getInstance();
    static final Endpoint twitterEndpoint = consumer.getEndpoint("api.twitter.com");
    
    public void init()
    {

    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
    	Log.info("TwitterPoastServlet: doGet");
        doPost(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
    	Log.info("TwitterPoastServlet: doPost");
        Token token = consumer.getToken(twitterEndpoint.getConsumerKey(), request);
        Log.info("TPS: Token consumer key: " + token.getCk());
        Log.info("TPS: Token key: " + token.getKey());
        Log.info("TPS: Token tostring: " + token.toString());
        switch(token.getState())
        {
            case Token.UNITIALIZED:
            	Log.info("TPS: Uninitialized");
            	Log.info("TPS: Callback url: " + request.getRequestURL().toString());
                UrlEncodedParameterMap params = new UrlEncodedParameterMap()
                    .add(Constants.OAUTH_CALLBACK, request.getRequestURL().toString());
                Log.info("TPS: doing consumer.fetchToken");    
                Response r = consumer.fetchToken(twitterEndpoint, params, TokenExchange.REQUEST_TOKEN, 
                        token);
                
                if(r.getStatus()==200 && token.getState()==Token.UNAUTHORIZED)
                {
                	Log.info("TPS: Uninitialized in if, so unauthorized");
                    // unauthorized request token
                	Log.info("TPS: Saving token");
                    consumer.saveToken(token, request, response);
                    StringBuilder urlBuffer = Transport.buildAuthUrl(
                            twitterEndpoint.getAuthorizationUrl(), token);
                    Log.info("TPS: response.sendRedirect(" + urlBuffer.toString() + ")");
                    response.sendRedirect(urlBuffer.toString());
                }
                break;
                
            case Token.UNAUTHORIZED:
            	Log.info("TwitterPoastServlet: Unauthorized");
            	Log.info("TwitterPoastServlet: token: " + request.getParameter(Constants.OAUTH_TOKEN));
            	Log.info("TwitterPoastServlet: verifier: " + request.getParameter(Constants.OAUTH_VERIFIER));
                if(token.authorize(request.getParameter(Constants.OAUTH_TOKEN), 
                        request.getParameter(Constants.OAUTH_VERIFIER)))
                {
                	Log.info("TwitterPoastServlet: Unauthorized: firstif");
                    if(fetchAccessToken(token, request, response))
                        sendTweet(token, request, response);
                    else
                    	Log.info("TPS: saving token");
                        consumer.saveToken(token, request, response);
                }
                break;
                
            case Token.AUTHORIZED:
            	Log.info("TwitterPoastServlet: Authorized");
                if(fetchAccessToken(token, request, response))
                    sendTweet(token, request, response);
                break;
                
            case Token.ACCESS_TOKEN:
            	Log.info("TwitterPoastServlet: Access token");
                sendTweet(token, request, response);
                break;
                
            default:
            	Log.info("TwitterPoastServlet: Default");
                response.sendRedirect(request.getContextPath() + "/index.html");
                //TODO make this do something proper, like trigger a servlet
                //that just sends an event about unsuccessful poast or idk
        }
    }
    
    public boolean fetchAccessToken(Token token, HttpServletRequest request, 
            HttpServletResponse response) throws IOException
    {
    	Log.info("TwitterPoastServlet: fetchAccessToken");
        // authorized request token
        UrlEncodedParameterMap params = new UrlEncodedParameterMap();
        
        Response r = consumer.fetchToken(twitterEndpoint, params, TokenExchange.ACCESS_TOKEN, token);
        if(r.getStatus()==200 && token.getState()==Token.ACCESS_TOKEN)
        {
            // access token
            consumer.saveToken(token, request, response);
            return true;
        }
        return false;
    }
    
    protected void sendTweet(Token token, HttpServletRequest request, 
            HttpServletResponse response) throws IOException
    {
    	Log.info("TwitterPoastServlet: Send Tweet");
        Response r = doSend(TWITTER_SERVICE_URL, consumer.getConsumerContext(), twitterEndpoint, 
                token, request, response);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(r.getInputStream(), "UTF-8"));
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        for(String line=null; (line=br.readLine())!=null;)
            pw.append(line);
    }
    
    public static Response doSend(String serviceUrl, ConsumerContext context, Endpoint ep, 
            Token token, HttpServletRequest request, HttpServletResponse response) 
            throws IOException
    {
    	Log.info("TwitterPoastServlet: doSend " + (String)request.getParameter("message"));
        HttpConnector connector = context.getHttpConnector();
        UrlEncodedParameterMap params = new UrlEncodedParameterMap(serviceUrl);
        params.add("status", (String)request.getParameter("message"));
        context.getNonceAndTimestamp().put(params, token.getCk());
        Parameter authorization = new Parameter("Authorization", 
                HttpAuthTransport.getAuthHeaderValue(params, ep, token, 
                context.getNonceAndTimestamp(),  ep.getSignature()));
        return connector.doGET(params.getUrl(), authorization);
        
    }
    
}

