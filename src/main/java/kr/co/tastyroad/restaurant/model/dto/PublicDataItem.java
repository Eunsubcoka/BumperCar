package kr.co.tastyroad.restaurant.model.dto;



import com.google.gson.annotations.SerializedName;

public class PublicDataItem {

    @SerializedName("SIGUN_NM")
    private String sigunNm;

    @SerializedName("RESTRT_NM")
    private String restrtNm;

    @SerializedName("TASTFDPLC_TELNO")
    private String tastfdplcTelno;

    @SerializedName("REPRSNT_FOOD_NM")
    private String reprsntFoodNm;

    @SerializedName("REFINE_ZIPNO")
    private String refineZipno;

    @SerializedName("REFINE_ROADNM_ADDR")
    private String refineRoadnmAddr;

    @SerializedName("REFINE_LOTNO_ADDR")
    private String refineLotnoAddr;

    @SerializedName("REFINE_WGS84_LAT")
    private String refineWgs84Lat;

    @SerializedName("REFINE_WGS84_LOGT")
    private String refineWgs84Logt;

    // Getters and Setters
    public String getSigunNm() {
        return sigunNm;
    }

    public void setSigunNm(String sigunNm) {
        this.sigunNm = sigunNm;
    }

    public String getRestrtNm() {
        return restrtNm;
    }

    public void setRestrtNm(String restrtNm) {
        this.restrtNm = restrtNm;
    }

    public String getTastfdplcTelno() {
        return tastfdplcTelno;
    }

    public void setTastfdplcTelno(String tastfdplcTelno) {
        this.tastfdplcTelno = tastfdplcTelno;
    }

    public String getReprsntFoodNm() {
        return reprsntFoodNm;
    }

    public void setReprsntFoodNm(String reprsntFoodNm) {
        this.reprsntFoodNm = reprsntFoodNm;
    }

    public String getRefineZipno() {
        return refineZipno;
    }

    public void setRefineZipno(String refineZipno) {
        this.refineZipno = refineZipno;
    }

    public String getRefineRoadnmAddr() {
        return refineRoadnmAddr;
    }

    public void setRefineRoadnmAddr(String refineRoadnmAddr) {
        this.refineRoadnmAddr = refineRoadnmAddr;
    }

    public String getRefineLotnoAddr() {
        return refineLotnoAddr;
    }

    public void setRefineLotnoAddr(String refineLotnoAddr) {
        this.refineLotnoAddr = refineLotnoAddr;
    }

    public String getRefineWgs84Lat() {
        return refineWgs84Lat;
    }

    public void setRefineWgs84Lat(String refineWgs84Lat) {
        this.refineWgs84Lat = refineWgs84Lat;
    }

    public String getRefineWgs84Logt() {
        return refineWgs84Logt;
    }

    public void setRefineWgs84Logt(String refineWgs84Logt) {
        this.refineWgs84Logt = refineWgs84Logt;
    }
}


