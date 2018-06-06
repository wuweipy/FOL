package Common;

import java.util.HashMap;
import java.util.Iterator;

import Data.ruleInfo.DRole;
import Data.ruleInfo.RoleInfoDAO;


public class RoleMngt {
	
	public static final int Employee = 1;
	
	public static final int ProjectManager = 2;
	
	/***
	 * Ëù³¤
	 */
	public static final int InstitueLeader = 3;
	
	public static final int CFO = 4;
	
	public static final int CEO = 5;

	private static RoleMngt roleMngt = new RoleMngt();
	
	private HashMap<Integer, String> roleInfos = new HashMap<Integer, String>();
	
	
	private RoleMngt()
	{
		init();
	}

	private void init() 
	{
		RoleInfoDAO roleDao = new RoleInfoDAO();
		Iterator<DRole> roleIterator = roleDao.getAllRoleInfos().iterator();
		while(roleIterator.hasNext())
		{
			DRole role = roleIterator.next();
			roleInfos.put(role.getId(), role.getName());
		}
	}
	
	public static RoleMngt getInstance()
	{
		return roleMngt;
	}

	public HashMap<Integer, String> getAllRoleInfo() 
	{
		return roleInfos;
	}
	
}
