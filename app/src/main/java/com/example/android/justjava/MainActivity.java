package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    private int order_quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String userName = nameEditText.getText().toString();

        int price = calculatePrice(order_quantity,hasWhippedCream,hasChocolate);
        //displayMessage(createOrderSummary(price,hasWhippedCream,hasChocolate,userName));

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price,hasWhippedCream,hasChocolate,userName));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary of " + userName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity, boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if (hasWhippedCream){
            price += 1;
        }
        if (hasChocolate) {
            price += 2;
        }
        return quantity * price;
    }

    private String createOrderSummary (int price, boolean hasWhippedCream, boolean hasChocolate, String userName){

        String message = "Name: " + userName;
        message += "\nWhipped Cream added: " + hasWhippedCream;
        message += "\nChocolate added: " + hasChocolate;
        message += "\nQuantity: " + order_quantity;
        message += "\nTotal: $" + price;
        message += "\nThank you!";
        return message;
    }



    public void increment(View view) {
        if (order_quantity >= 100){
            order_quantity = 100;
            Toast.makeText(this, "You can't order more than 100 coffees!",Toast.LENGTH_SHORT).show();
        }
        else {
            order_quantity += 1;
        }
        displayQuantity(order_quantity);
    }

    public void decrement(View view) {
        if (order_quantity <= 0){
            order_quantity = 0;
            Toast.makeText(this, "Quantity can't be negative!",Toast.LENGTH_SHORT).show();
        }
        else{
            order_quantity -= 1;
        }
        displayQuantity(order_quantity);

    }
}