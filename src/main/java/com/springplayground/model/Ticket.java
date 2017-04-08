package com.springplayground.model;

import com.fasterxml.jackson.annotation.JsonProperty;

// Cannot use lombok, as JsonProperty getters and setters use different cases
public class Ticket {
    Integer price;
    Passenger passenger;

    public Ticket() {
    }

    @JsonProperty("Price")
    public Integer getPrice() {
        return this.price;
    }

    @JsonProperty("Passenger")
    public Passenger getPassenger() {
        return this.passenger;
    }

    @JsonProperty("price")
    public void setPrice(Integer price) {
        this.price = price;
    }

    @JsonProperty("passenger")
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Ticket)) return false;
        final Ticket other = (Ticket) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$passenger = this.getPassenger();
        final Object other$passenger = other.getPassenger();
        if (this$passenger == null ? other$passenger != null : !this$passenger.equals(other$passenger)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $passenger = this.getPassenger();
        result = result * PRIME + ($passenger == null ? 43 : $passenger.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Ticket;
    }

    public String toString() {
        return "com.springplayground.model.Ticket(price=" + this.getPrice() + ", passenger=" + this.getPassenger() + ")";
    }
}
