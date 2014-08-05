package study.ejb3.security.ejbs;

import java.security.Principal;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.resource.spi.SecurityException;

import study.ejb2.security.ejbs.AccountBalanceException;

/**
 * In EJB 3.x we now can define the security policy through annotations (which are
 * overrided when also is defined in ejb-jar.xml).
 * 
 * The declarative security policy can be defined in the bean using annotations
 * or in the XML deployment descriptor. Declarative authorization involves
 * declaring roles, assigning permission to methods (or to the entire bean),
 * or changing temporarily a security identity. These controls are made by the
 * annotations
 * 
 * Available Annotations (is the same used with Servlets 3.x):
 * 
 * > @PermitAll
 * . Can be used in a bean-level or method-level;
 * . Indicate that the bean or method accessible by everyone.
 * > @DenyAll
 * . Only in bean-level;
 * . Indicates that no role is permitted to execute the specified method.
 * > @RolesAllowed("<role-name>","<role-name>",...)
 * . Bean or method;
 * . Indicates that a list of roles is allowed to execute the given method or bean.
 * > @DeclareRoles
 * . Bean;
 * . Defines roles for security checking.
 * > @RunAs
 * . Bean;
 * . Temporarily assigns a new role to a principal.
 * 
 * The container will automatically declare roles by inspecting the @RolesAllowed annotation
 * or @DeclareRoles.
 */
@Stateful
@DeclareRoles({"Customer","DBA"})
@RolesAllowed({"Customer","Manager","Admin"})
//@RunAs("Customer")
public class AccountBean {
	private SessionContext ctx;
	private String account;
	private String name;
	private double balance;

	public void load(String numAccount, String name, double balance) {
		//int numAccount = (int) (Math.random() * 10000000);
		this.account = String.valueOf(numAccount);
		this.name = name;
		this.balance = balance;
	}
	
	@RolesAllowed("Manager")
	@Remove
	public void deleteAccount() {
		this.account = null;
		this.name = null;
		balance = 0;
	}

	// methods from component interface
	public String getInformation() {
		return account + " - " + name + ": " + balance;
	}

	public double getBalance() {
		return balance;
	}

	@DenyAll
	@RolesAllowed("Customer")
	public void withdraw(double amount) throws AccountBalanceException, SecurityException {
		if (balance - amount < 0) {
			ctx.setRollbackOnly();
			throw new AccountBalanceException("There isn't enough money.");
		} else if (!ctx.isCallerInRole("EjbRoleCustomer")) {
			throw new SecurityException("Not authorized.");
		} else {
			Principal princ = ctx.getCallerPrincipal();
			System.out.println("Somebody is withdrawing: " + princ.getName());
			balance -= amount;
		}
	}
}
