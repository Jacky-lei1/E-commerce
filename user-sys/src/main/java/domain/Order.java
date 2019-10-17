package domain;

/**订单的实体类
 * @author lvyelanshan
 * @create 2019-10-16 16:25
 */
public class Order {

    private String id;

    private Double total;

    private String date;

    public Order(String id, Double total, String date) {
        this.id = id;
        this.total = total;
        this.date = date;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", total=" + total +
                ", date='" + date + '\'' +
                '}';
    }
}
