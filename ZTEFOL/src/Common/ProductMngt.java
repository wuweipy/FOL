package Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import Data.Common.DProduct;
import Data.Common.IProductDAO;
import Data.Common.ProductDAO;
import Data.Common.Entry;
import Data.DBHandler.DBConnectorFactory;

public class ProductMngt 
{
	public static final int Sales = 6;
	
	private static Logger logger = FOLLogger.getLogger(ProductMngt.class);
	private static ProductMngt productMngt = new ProductMngt();
	private HashMap<Integer, String> products = new HashMap<Integer, String>();
	
	private ProductMngt()
	{
		init();
	}

	private void init() 
	{
        IProductDAO productDAO = new ProductDAO();
        Iterator<DProduct> iterator = productDAO.getProductList().iterator();
        while(iterator.hasNext())
        {
        	DProduct dept = iterator.next();
        	products.put(dept.getId(), dept.getName());
        }
	}
	
	public static ProductMngt getInstance() {
		return productMngt;
	}

	public String getProductName(int productNo) {
		return products.get(productNo);
	}
	
	public HashMap<Integer, String> getProducts()
	{
		return products;
	}

	public static HashMap<Integer, ArrayList<Entry>> getSubProductList() 
	{
			HashMap<Integer, ArrayList<Entry>> subProducts = new HashMap<Integer, ArrayList<Entry>>();
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			if(connection == null)
			{
				return subProducts;
			}
			Statement statement = null;
			ResultSet rs = null;
			try 
			{
				statement = connection.createStatement();
			    rs = statement.executeQuery("SELECT * from SUBPRODUCT where SUBPRODUCT.delete = 0;");
				while (rs.next()) 
				{
					ArrayList<Entry> list = subProducts.get(rs.getInt("item"));
					if(list == null)
					{
					   list = new ArrayList<Entry>();
					}
					Entry entry = new Entry();
					entry.setId(rs.getInt("id"));
					entry.setName(rs.getString("subItem"));
                    list.add(entry);
					subProducts.put(rs.getInt("item"), list);
			    }
			}
			catch (SQLException e) 
			{
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
			}
			return subProducts;
	}
	
	public static HashMap<Integer, String> getSubProducts() 
	{
			HashMap<Integer, String> subProducts = new HashMap<Integer, String>();
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			if(connection == null)
			{
				return subProducts;
			}
			Statement statement = null;
			ResultSet rs = null;
			try 
			{
				statement = connection.createStatement();
			    rs = statement.executeQuery("SELECT * from SUBPRODUCT");
				while (rs.next()) 
				{
					subProducts.put(rs.getInt("id"), rs.getString("subItem"));
			    }
			}
			catch (SQLException e) 
			{
				logger.error(e.getMessage());
			}
			finally
			{
				DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
			}
			return subProducts;
	}
	
	public static HashMap<Integer, String> getSubProduct(int deptId) 
	{
			HashMap<Integer, String> subProducts = new HashMap<Integer, String>();
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			if(connection == null)
			{
				return subProducts;
			}
			Statement statement = null;
			ResultSet rs = null;
			try 
			{
				statement = connection.createStatement();
			    rs = statement.executeQuery("SELECT * from SUBPRODUCT where SUBPRODUCT.isDelete = 0 and item='"+deptId+"'");
				while (rs.next()) 
				{
					subProducts.put(rs.getInt("id"), rs.getString("subItem"));
			    }
			}
			catch (SQLException e) 
			{
				logger.error(e.getMessage());
			}
			finally
			{
				DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
			}
			return subProducts;
	}

	public static boolean addSubProduct(int productId, String subProductName,String userId) {
		// TODO Auto-generated method stub
		Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
		if(connection == null)
		{
			return false;
		}
		PreparedStatement statement = null;
		try 
		{
			String sql = "insert into SUBPRODUCT (item,subItem,owner) values (?,?,?)";
			statement = connection.prepareStatement(sql);
		    statement.setInt(1, productId);
		    statement.setString(2,subProductName);
		    statement.setString(3,userId);
			return statement.executeUpdate() == 1;	 
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		finally
		{
			DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
		} 
		return false;
	}

	public static boolean deleteSubProduct(int subProductId) {
		// TODO Auto-generated method stub
        Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
        if(connection == null)
        {
            return false;
        }
        PreparedStatement statement = null;        
        try 
        {
            String sql = "update SUBPRODUCT set SUBPRODUCT.delete=1 where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, subProductId);        
            return statement.executeUpdate()==1;            
        }
        catch (SQLException e) 
        {
            logger.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        finally
        {
            DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
        }         
        return false;
	}

	public static boolean modifySubProduct(int subProductId, String subName) {
		// TODO Auto-generated method stub
        Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
        if(connection == null)
        {
            return false;
        }
        PreparedStatement statement = null;        
        try 
        {
            String sql = "update SUBPRODUCT set subItem=? where id=?";
            statement = connection.prepareStatement(sql);           
            statement.setString(1, subName); 
            statement.setInt(2, subProductId);        
            return statement.executeUpdate()==1;            
        }
        catch (SQLException e) 
        {
            logger.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        finally
        {
            DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, null);
        }         
        return false;
	}

	public static Product getSubProductById(int subProductId) {
		// TODO Auto-generated method stub
        Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
        if(connection == null)
        {
            return null;
        }
        PreparedStatement statement = null;
        ResultSet rs = null;
        try 
        {
            String sql = "select * from SUBPRODUCT where id=?";
            statement = connection.prepareStatement(sql);           
            statement.setInt(1, subProductId);        
            rs = statement.executeQuery();  
            while(rs.next())
            {
            	Product product = new Product();
            	product.setId(rs.getInt("id"));
            	product.setName(rs.getString("subItem"));
            	product.setParentProductId(rs.getInt("item"));
            	product.setOwner(rs.getString("owner"));
            	return product;
            }
        }
        catch (SQLException e) 
        {
            logger.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        finally
        {
            DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
        }         
		return null;
	}
	
	public static void main(String[] args) {
		HashMap<Integer, ArrayList<Entry>> map =  getSubProductList();
		System.out.println(map);
	}
	
}
