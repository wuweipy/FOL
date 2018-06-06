package View;

public class BoePageStatus {
	
	public static final int FIRST = 0;
	
	public static final int SECOND = 1;
	
	public static final int THIRD = 2;
	
	public static final int LAST = 3;
	
	public static final int NOACTION = 0;
	
	public static final int SAVEACTION = 1;
	
	public static final int NEXTACTION = 2;
	
	private int page = 0;
	
	private int action = 0;

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public void setAction(String inputAction) throws Exception
	{
	    switch (page) {
		case FIRST:
			if(inputAction.equalsIgnoreCase("save"))
			{
				action = SAVEACTION;
			}
			if(inputAction.equalsIgnoreCase("next"))
			{
				page = SECOND;
				action = NOACTION;
			}
			break;
		case SECOND:
			if(inputAction.equalsIgnoreCase("save"))
			{
				action = SAVEACTION;
			}
			if(inputAction.equalsIgnoreCase("next"))
			{
				page = THIRD;
				action = NOACTION;
			}
			if(inputAction.equalsIgnoreCase("pre"))
			{
				page = FIRST;
				action = NOACTION;
			} 
			break;
		case THIRD:
			if(inputAction.equalsIgnoreCase("save"))
			{
				action = SAVEACTION;
			}
			if(inputAction.equalsIgnoreCase("next"))
			{
				page = LAST;
				action = NOACTION;
			}
			if(inputAction.equalsIgnoreCase("pre"))
			{
				page = SECOND;
				action = NOACTION;
			} 
			break;
		default:
			break;
		}
	}
	
	

}
