package com.ldap.main;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPSearchConstraints;
import netscape.ldap.LDAPSearchResults;
import netscape.ldap.controls.LDAPEntryChangeControl;
import netscape.ldap.controls.LDAPPersistSearchControl;

import com.ldap.clients.AddEntryMonitor;
import com.ldap.clients.UpdateEntryMonitor;

/**
 * ESB Load Test
 *
 */
public class LdapMain implements Runnable{
  private ExecutorService executor;
  private ExecutorService dbExecutor;
  
  /**
 * 
 */
public LdapMain() {
	  
	  executor =  Executors.newFixedThreadPool(50);
	  dbExecutor =  Executors.newFixedThreadPool(10);
	 
  }

  

  public static void main( String[] args ) {

    Thread th = new Thread( new LdapMain(), "mainConn" );
    th.start();
    System.out.println( "Main thread started." );
  }

  public void run(){
	  LDAPConnection ld = new LDAPConnection();

      try {

         /* Connect to server */

         ld.connect("172.29.210.103", 389);

         /* Create a persistent search control. */

         int op = LDAPPersistSearchControl.ADD |

            LDAPPersistSearchControl.MODIFY |

            LDAPPersistSearchControl.DELETE |

            LDAPPersistSearchControl.MODDN;

         boolean changesOnly = true;

         boolean returnControls = true;

         boolean isCritical = true;
         
         LDAPPersistSearchControl persistCtrl = new
         LDAPPersistSearchControl( op, changesOnly,
         returnControls, isCritical );
         
         LDAPSearchConstraints cons = ld.getSearchConstraints();

         /* Create search constraints to use that control. */


         cons.setServerControls( persistCtrl );

         /* Start the persistent search. */
         String filter = "(objectclass=*)";
         String getAttrs[] = { "uid" };
         
         netscape.ldap.LDAPSearchResults res = ld.search( "ou=people,o=isd",
        		 LDAPConnection.SCOPE_SUB,filter, null, false,cons);

         while ( res.hasMoreElements() ) {
             int changeType = getChangeType(res);
             /* Get the next directory entry. */
         	
             LDAPEntry findEntry = null;
 			try {
 				findEntry = res.next();
 			} catch (LDAPException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
         switch(changeType){
         
         case 1:{
        	 AddEntryMonitor addEntryMonitor = new AddEntryMonitor(findEntry);
        	 Future fu = executor.submit(addEntryMonitor);
        	 System.out.println("AddEntry thread status "+fu.isDone());
        	 break;
         }
         case 4:{
        	 UpdateEntryMonitor addEntryMonitor = new UpdateEntryMonitor(findEntry);
        	 Future fu = executor.submit(addEntryMonitor);
        	 System.out.println("UpdateEntry thread status "+fu.isDone());
        	 break;
         }
         
         
         }
         }
  }catch(Exception e){
	  e.printStackTrace();
  }
  }
  
  public int getChangeType(LDAPSearchResults res){
	  
	  /* Get any entry change controls. */

      LDAPControl[] responseCtrls = res.getResponseControls();
      int changeType=0;

      if ( responseCtrls != null ) {

         for ( int i=0; i<responseCtrls.length; i++ ){

            if (!(responseCtrls[i] instanceof

                  LDAPEntryChangeControl)){

               continue;

            }

            LDAPEntryChangeControl entryCtrl =

               (LDAPEntryChangeControl) responseCtrls[i];



            /* Get information on the type of change made. */

          changeType = entryCtrl.getChangeType();
          System.out.println("entry id :"+entryCtrl.getValue().toString());

            if ( changeType != -1 ) {

               System.out.print( "Change made: " );

               switch ( changeType ) {

               case LDAPPersistSearchControl.ADD:

                  System.out.println( "Added new entry." );

                  break;

               case LDAPPersistSearchControl.MODIFY:

                  System.out.println( "Modified entry." );

                  break;

               case LDAPPersistSearchControl.DELETE:

                  System.out.println( "Deleted entry." );

                  break;

               case LDAPPersistSearchControl.MODDN:

                  System.out.println( "Renamed entry." );

                  break;

               }

            }

	  
  }
}
      return changeType;
  }
  
  
  
  }
