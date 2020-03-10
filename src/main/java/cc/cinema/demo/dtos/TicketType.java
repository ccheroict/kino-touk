package cc.cinema.demo.dtos;

public enum TicketType {
    ADULT(25),
    STUDENT(18),
    CHILD(12.5);

    private final double price;

    public double getPrice() {
        return price;
    }

    TicketType(double price) {
        this.price = price;
    }
}
