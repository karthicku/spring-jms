package com.ldap.clients;

import java.util.Enumeration;

import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPEntry;

public class UpdateEntryMonitor implements Runnable{

	private LDAPEntry findEntry;
	public UpdateEntryMonitor(LDAPEntry findEntry){
		this.findEntry = findEntry;
	}
	
	public void run(){
	
	System.out.println("Entering UpdateEntryMonitor Thread :");

            /* Get the set of attributes for that entry. */

            LDAPAttributeSet findAttrs = findEntry.getAttributeSet();

            Enumeration enumAttrs = findAttrs.getAttributes();


           // Iterator attrbs = findAttrs.iterator();
            /* Iterate through the attributes. */
            String attrName = null;
            while ( enumAttrs.hasMoreElements() ) {

               LDAPAttribute anAttr =

                  (LDAPAttribute)enumAttrs.nextElement();

               attrName = anAttr.getName();

               



               /* Get the set of values for each attribute. */

               Enumeration enumVals = anAttr.getStringValues();



               /* Iterate through the values and print each value. */

               while (enumVals.hasMoreElements() ) {

                  String aVal = (String)enumVals.nextElement();

                  System.out.println(attrName + "\t\t" + aVal );

               }

            }
	}

}
