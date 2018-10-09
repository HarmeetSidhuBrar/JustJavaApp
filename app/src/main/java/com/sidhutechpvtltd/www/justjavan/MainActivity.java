package com.sidhutechpvtltd.www.justjavan;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class
MainActivity extends AppCompatActivity {
    int quantity = 2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity==100){
            //show an error message as a toast.
            Toast.makeText(this,"you cannot order more than 100 cups of coffee",Toast.LENGTH_SHORT).show();
          //Exit this method early because there is noting left to do.
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity==1){
        //show an error message as a toast.
            Toast.makeText(this,"you have to order at least 1 cup of coffee",Toast.LENGTH_SHORT).show();
        //Exit this method early because there is noting left to do.
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText editTextBox = (EditText) findViewById(R.id.edit_text);
        String thisText = editTextBox.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.add_chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price=calculatePrice(hasWhippedCream , hasChocolate);
        String priceMessage = createOrderSummery(price, hasWhippedCream ,hasChocolate,thisText);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + thisText);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);

        }

    }

    /**
     * Calculates the price of the order.
     *@param addChocolate if user choose chocolate topping.
     *@param addWhippedCream if user choose Whipped cream.
     *  @return total price.
     */
    private int calculatePrice(boolean addWhippedCream , boolean addChocolate) {
    //price of 1 cup of coffee.
        int basePrice = 5 ;
    //add $1 if user wants whippedCream.
        if (addWhippedCream){
            basePrice = basePrice + 1;
            }
    //add $2 if user wants chocolate.
        if (addChocolate)  {
            basePrice = basePrice + 2 ;
        }
    //Calculate the total price by multiplying base price with quantity.
        return basePrice * quantity;
    }

    /**
     * Create summary of the Order.
     * @param editTextValue is the name of user from EditText View.
     *@param addWhippedCream is whether or not user wants whipped cream toppings.
     * @param addChocolate is whether or not user wants chocolate toppings.
     * @param price of the order.
     * @return text summary.
     */
    private String createOrderSummery(int price , boolean addWhippedCream , boolean addChocolate, String editTextValue){
        String priceMessage = "Name: " + editTextValue;
        priceMessage+= "\nAdd whipped cream? " + addWhippedCream;
        priceMessage+="\nAdd Chocolate ? " + addChocolate;
        priceMessage+= "\nQuantity:"+ quantity;
        priceMessage+= "\nTotal : $" + price;
        priceMessage= priceMessage +"\n Thank You!";
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int noOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + noOfCoffees);
    }


}
