package com.ollijepp.poastr.shared.socialService;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

@Cached @Unindexed
public class AccountOwner implements Serializable {
	private static final long serialVersionUID = 6478732920021054508L;
	@Id Long id;
	private String accountOwnerHash;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Constructor used for serialization.
	 */
	public AccountOwner() {
		super();
	}
	public String getAccountOwnerHash() {
		return accountOwnerHash;
	}
	public void setAccountOwnerHash(String accountOwnerHash) {
		this.accountOwnerHash = accountOwnerHash;
	}
	public AccountOwner(String accountOwnerHash) {
		super();
		this.accountOwnerHash = accountOwnerHash;
	}
}
