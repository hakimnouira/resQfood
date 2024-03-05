package models;

public class Bans {


	  private  int BanID ;
	  private  int 	UserID	;
      private  int BannedBy	;
    private	 String BanReason ;

    public int getBanID() {
        return BanID;
    }

    public void setBanID(int banID) {
        BanID = banID;
    }

    public int getUserID() {
        return UserID;
    }

    public Bans() {
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getBannedBy() {
        return BannedBy;
    }

    public void setBannedBy(int bannedBy) {
        BannedBy = bannedBy;
    }

    public String getBanReason() {
        return BanReason;
    }

    public void setBanReason(String banReason) {
        BanReason = banReason;
    }


    public Bans(int userID, int bannedBy, String banReason) {
        UserID = userID;
        BannedBy = bannedBy;
        BanReason = banReason;
    }
}
