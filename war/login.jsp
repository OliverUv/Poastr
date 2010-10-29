<%@ page session="false" %>
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>Sign-In</title>
	<link rel="stylesheet" href="/openid/style.css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
	<script type="text/javascript" src="/openid/openid-jquery.js"></script>
	<script type="text/javascript">
	//<!--
		$(document).ready(function() {
		    openid.init('openid_identifier');
		});
	//-->
	</script>
	<!-- /Simple OpenID Selector -->
	
	<style type="text/css">
		body {
			font-family:"Helvetica Neue", Helvetica, Arial, sans-serif;
		}
    #container {
      padding: 1em;      
    }
    
    #container p {
      font-size: 1.2em;
      font-family: Tahoma,Verdana;
    }
    
    #openid_identifier {
      background-image: url(http://www.openid.net/login-bg.gif);
      background-position: 3px 2px;
      background-repeat: no-repeat;
      margin: 0;
      padding: 0.2em 0.2em 0.2em 20px;
      vertical-align: middle;
      width: 322px;
    }
</style>
</head>
<body>
<form method="post" id="openid_form">
	<fieldset>
    		<legend>Sign-in with your OpenID account</legend>
    		<div id="openid_choice">
	    		<p>Please click your account provider:</p>
	    		<div id="openid_btns"></div>
			</div>
			<div id="openid_input_area">
				<input id="openid_identifier" name="openid_identifier" type="text" size="80"/>
    			<input id="openid_submit" class="btn" type="submit" value="Sign-In"/>
				<input type="hidden" name="action" value="verify" />
			</div>
	</fieldset>
</form>
</body>
</html>
