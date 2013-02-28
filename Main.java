public class Main {
    public static void main(String[] args) {
	Runtime.getRuntime().addShutdownHook(new Thread(){
		@Override
		public void run(){
		    try{
			shutdownDB();
		    }
		    catch(SQLException ex){
			ex.printStackTrace();
		    }
		}
	    });
	DBSample db = new DBSample();
	db.run();
	

    }
}
