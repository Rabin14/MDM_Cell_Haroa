package block.mdmcellharoa;


public class ProductsModel {

    private String total;
    private String present;
    private String date;

    private ProductsModel(){}
    private ProductsModel(String date, String total, String present){

        this.date= date;
        this.present= present;
        this.total= total;

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }



}
