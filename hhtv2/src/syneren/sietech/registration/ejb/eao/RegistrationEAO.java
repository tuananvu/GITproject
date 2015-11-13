package syneren.sietech.registration.ejb.eao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import syneren.sietech.registration.RegistrationBean;
import syneren.sietech.registration.ejb.entity.Role;
import syneren.sietech.registration.ejb.entity.User;
import syneren.sietech.registration.util.Util;

/**
 * Session Bean implementation class RegistrationEAO
 */
@Stateless(mappedName = "registrationEAO")
@LocalBean
public class RegistrationEAO {

	@PersistenceContext()
	EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public RegistrationEAO() {
        // TODO Auto-generated constructor stub
    }

	public boolean persistUser(RegistrationBean registrationBean) {
		try {
			User user = new User();
			user.setUsername(registrationBean.getUsername());
			user.setPassword(Util.passwordGenerate(registrationBean.getPassword()));
			user.setFirstname(registrationBean.getFirstname());
			user.setLastname(registrationBean.getLastname());
			user.setBirth(registrationBean.getDob());
			user.setEmail(registrationBean.getEmail());
			
			String[] targets = registrationBean.getSelectedRoles();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT c FROM Role c WHERE c.rolename=?1");
			int count = 2;
			for(int x=1; x<targets.length; x++){
				buffer.append(" OR c.rolename=?" + count);
			}
			
			count = 1;
			Query query = this.entityManager.createQuery(buffer.toString());
			for (String target : targets) {
				query.setParameter(count, target);
				count++;
			}
			
			List<Role> roles = Util.castList(Role.class, query.getResultList());
			for(Role role : roles){
				role.getUsers().add(user);
			}
			user.setRoles(roles);
			
			//commit data to the database
			entityManager.persist(user);
			return true;
		} catch (ClassCastException e) {
			System.out.println("Class Cast Exception");
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			System.out.println("Illegal Argument Exception");
			e.printStackTrace();
		} catch(Exception e){
			System.out.println("Generic Exception");
			e.printStackTrace();
		}
		
		return false;
		
	}

}
