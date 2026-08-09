package com.services;

import servicesimpl.AccountServicesImplm;

public class BusinessObjectCreator {
     public static AccountServices getAccountService() {
    	 return new AccountServicesImplm();
     }
}
