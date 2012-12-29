package org.test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.view.View;
import android.view.View.OnClickListener;

public class CaculatorActivity extends Activity {
	GridView mKeypadGrid;
	TextView userInputText;

	Stack<String> mInputStack;
	Stack<String> mOperationStack;

	KeypadAdapter mKeypadAdapter;
	boolean resetInput = false;
	boolean hasFinalResult = false;
	boolean isRN=true;

	String mDecimalSeperator;
	double memoryValue = Double.NaN;
	
	RNcalculator rc= new RNcalculator();
    String capital="", ain="", years="", times="", interest="";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DecimalFormat currencyFormatter = (DecimalFormat) NumberFormat
				.getInstance();
		char decimalSeperator = currencyFormatter.getDecimalFormatSymbols()
				.getDecimalSeparator();
		mDecimalSeperator = Character.toString(decimalSeperator);

		setContentView(R.layout.main);

		// Create the stack
		mInputStack = new Stack<String>();
		mOperationStack = new Stack<String>();

		// Get reference to the keypad button GridView
		mKeypadGrid = (GridView) findViewById(R.id.grdButtons);

		// Get reference to the user input TextView
		userInputText = (TextView) findViewById(R.id.txtInput);
		userInputText.setText("");
	
		// Create Keypad Adapter
		mKeypadAdapter = new KeypadAdapter(this);

		// Set adapter of the keypad grid
		mKeypadGrid.setAdapter(mKeypadAdapter);

		// Set button click listener of the keypad adapter
		mKeypadAdapter.setOnButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button btn = (Button) v;
				KeypadButton keypadButton = (KeypadButton) btn.getTag();
				ProcessKeypadInput(keypadButton);
			}
		});

		mKeypadGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
			}
		});
	}

	private void ProcessKeypadInput(KeypadButton keypadButton) {
		
		String text = keypadButton.getText().toString();
		String currentInput = userInputText.getText().toString();  
		int currentInputLen = currentInput.length();
		String evalResult = null;
		
		switch (keypadButton) {
		case BACKSPACE: // Handle backspace
			if (resetInput)
				return;
			int endIndex = currentInputLen - 1;
			if (endIndex < 1) {
				userInputText.setText("");
			}
			else {
				userInputText.setText(currentInput.subSequence(0, endIndex));
			}
			break;
		
		case CE: // Handle clear input
			userInputText.setText("");
			clearStacks();
			break;
			
		case SwitchMode:
			if(isRN)
			{	 isRN=false;
			userInputText.setText("0");
			}
			else
			{	isRN=true;
			userInputText.setText("");}
			clearStacks();
			break;
			
		case DECIMAL_SEP: // Handle decimal seperator
			if(!isRN){
			if (hasFinalResult || resetInput) {
				userInputText.setText("0" + mDecimalSeperator);
				hasFinalResult = false;
				resetInput = false;
			} else if (currentInput.contains("."))
				return;
			else
				userInputText.append(mDecimalSeperator);
			break;
			}
			else break;
			
		case ConvertoDecimal:
			if(isRN)
			{
				evalResult = rc.convertRNtoDecimal(currentInput);
				if (evalResult != null) {
					clearStacks();
					userInputText.setText(evalResult);
					resetInput = false;
					hasFinalResult = true;		
			     }
			}
			break;
			
		case ConvertoRN:
			if(!isRN)
			{
				evalResult = rc.convertoRN(currentInput);
			if (evalResult != null) {
				clearStacks();
				userInputText.setText(evalResult);
				resetInput = false;
				hasFinalResult = true;		
		     }	
			}
			break;
		
		case DIV:
		case PLUS:
		case MINUS:
		case MULTIPLY:
			if(isRN){
			if (resetInput) {
				mInputStack.pop();
				mOperationStack.pop();
			} else {			
				mInputStack.add(currentInput);
				mOperationStack.add(currentInput);
			}

			mInputStack.add(text);
			mOperationStack.add(text);

			dumpInputStack();
			evalResult = evaluateResult(false);
			if (evalResult != null)
				userInputText.setText(evalResult);
			resetInput = true;
			break;
			}
			else break;
			
		case CALCULATE:
			if(isRN) {
			if (mOperationStack.size() == 0)
				break;

			mOperationStack.add(currentInput);
			evalResult = evaluateResult(true);
			if (evalResult != null) {
				clearStacks();
				userInputText.setText(evalResult);
				resetInput = false;
				hasFinalResult = true;
			}
			break;
			}else break;
			
		case Principal:
			capital=currentInput;
			resetInput = true;
			break;
			
		case Anualinterest:
				ain=currentInput;
				resetInput = true;
			break;
			
		case Timesperyear:
			times=currentInput;
			resetInput = true;
			break;
			
		case Years:
			years=currentInput;
			resetInput = true;
			break;
			
		case ComInterest:
			if(isRN)
			{
				String res = rc.CompoundInterest(capital, ain, times, years);
				if (res != null) {
					clearStacks();
					userInputText.setText(res);
					resetInput = false;
					hasFinalResult = true;		
			     }	
			}
			break;
			
		default:
			if(isRN){
			if(text.charAt(0)=='M'||text.charAt(0)=='D'||text.charAt(0)=='C'||text.charAt(0)=='L'
			||text.charAt(0)=='X'||text.charAt(0)=='V'||text.charAt(0)=='I')
	{
		if (currentInput.equals("") || resetInput || hasFinalResult) {
			userInputText.setText(text);
			resetInput = false;
			hasFinalResult = false;
		} else {
			userInputText.append(text);
			resetInput = false;
		}
	}
			}
			else
			{
				if (Character.isDigit(text.charAt(0))) {
					if (currentInput.equals("") || resetInput || hasFinalResult) {
						userInputText.setText(text);
						resetInput = false;
						hasFinalResult = false;
					} else {
						userInputText.append(text);
						resetInput = false;
					}
				}
			}			
		}		
	}
	
	private void clearStacks() {
		capital=""; ain=""; times=""; years="";
		mInputStack.clear();
		mOperationStack.clear();
	}

	private void dumpInputStack() {
		Iterator<String> it = mInputStack.iterator();
		StringBuilder sb = new StringBuilder();

		while (it.hasNext()) {
			CharSequence iValue = it.next();
			sb.append(iValue);
		}
	}

	private String evaluateResult(boolean requestedByUser) {
		if ((!requestedByUser && mOperationStack.size() != 4)
				|| (requestedByUser && mOperationStack.size() != 3))
			return null;

		String left = mOperationStack.get(0);
		String operator = mOperationStack.get(1);
		String right = mOperationStack.get(2);
		String tmp = null;
		String resultStr = "";
		if (!requestedByUser)
			tmp = mOperationStack.get(3);

		if (operator.equals(KeypadButton.DIV.getText())) {
			resultStr=rc.operations(left, right, 4);
		} else if (operator.equals(KeypadButton.MULTIPLY.getText())) {
			resultStr=rc.operations(left, right, 3);

		} else if (operator.equals(KeypadButton.PLUS.getText())) {
			resultStr=rc.operations(left, right, 1);
			
		} else if (operator.equals(KeypadButton.MINUS.getText())) {
			resultStr=rc.operations(left, right, 2);
		}

		if (resultStr == null)
			return null;

		mOperationStack.clear();
		if (!requestedByUser) {
			mOperationStack.add(resultStr);
			mOperationStack.add(tmp);
		}
		return resultStr;
	}
}
