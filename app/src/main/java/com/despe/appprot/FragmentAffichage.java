package com.despe.appprot;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAffichage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAffichage extends Fragment implements View.OnClickListener {

    private static final String TAG = "MSG_DEBUG";
    // Widgets
    Button button;
    ListView deviceslist;
    TextView barycentreX_label, barycentreX, barycentreY_label, barycentreY;
    ImageView circle_label, line_abs_label, line_ord_label;
    GraphView graph;

    // Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public String device_address = "";
    private ProgressDialog progress;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Toast toast;
    public static FragmentAffichage newInstance() {
        return (new FragmentAffichage());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "[FragmentAffichage] onCreate");
        super.onCreate(savedInstanceState);
        this.askForBluetooth();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "[FragmentAffichage] onCreateView");
        // Inflate the layout of FragmentAffichage
        View view = inflater.inflate(R.layout.fragment_affichage, container, false);

        // Call the widgets
        button = view.findViewById(R.id.button);
        deviceslist = view.findViewById(R.id.listView);
        barycentreX_label = view.findViewById(R.id.barycentreX_label);
        barycentreX = view.findViewById(R.id.barycentreX);
        barycentreY_label = view.findViewById(R.id.barycentreY_label);
        barycentreY = view.findViewById(R.id.barycentreY);
        circle_label = view.findViewById(R.id.circle_label);
        line_abs_label = view.findViewById(R.id.line_abs_label);
        line_ord_label = view.findViewById(R.id.line_ord_label);

        barycentreX_label.setVisibility(View.INVISIBLE);
        barycentreY_label.setVisibility(View.INVISIBLE);
        circle_label.setVisibility(View.INVISIBLE);
        line_abs_label.setVisibility(View.INVISIBLE);
        line_ord_label.setVisibility(View.INVISIBLE);

        graph = view.findViewById(R.id.graph);
        graph.setVisibility(View.INVISIBLE);

        toast = Toast.makeText(getActivity(), "Vous avez été déconnecté. Vérifiez votre connexion bluetooth, puis recommencez", Toast.LENGTH_LONG);

        // Set onClickListener to the button
        button.setOnClickListener(this);

        return view;
    }

    public void askForBluetooth() {
        //we check if the device has got the bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        //if not...
        if (myBluetooth == null) {
            //show a message that the device has no bluetooth adapter
            Toast.makeText(getActivity(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            //finish apk
            getActivity().finish();
        }
        //else, if the device has the bluetooth adapter, but it's off...
        else if (!myBluetooth.isEnabled()) {
            //Ask to the user to turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }
    }

    @Override
    public void onClick(View v) {
        //we check if the device has got the bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        pairedDevicesList(); //method that will be called
    }

    private void pairedDevicesList() {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();
        // if there is at least one paired device
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                list.add(bt.getName() + "\n" + bt.getAddress()); //get the device's name and the address
            }
        } else {
            Toast.makeText(getActivity(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        deviceslist.setAdapter(adapter);
        deviceslist.setOnItemClickListener(myListClickListener); //method called when the device from the list is clicked
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {
            button.setVisibility(View.INVISIBLE);
            deviceslist.setVisibility(View.INVISIBLE);
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            device_address = info.substring(info.length() - 17);

            // Connection to the Bluetooth device chosen
            boolean ConnectSuccess = true; //if it's here, it's almost connected
            progress = ProgressDialog.show(getActivity(), "Connecting...", "Please wait!!!");  //show a progress dialog
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(device_address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            if (!ConnectSuccess) {
                Toast.makeText(getActivity(), "Connection Failed. Is it a SPP Bluetooth? Try again.", Toast.LENGTH_LONG).show();
                getActivity().finish(); //return to the first layout
            } else {
                Toast.makeText(getActivity(), "Connecté", Toast.LENGTH_LONG).show();
                isBtConnected = true;
            }
            progress.dismiss();

            barycentreX_label.setVisibility(View.VISIBLE);
            barycentreY_label.setVisibility(View.VISIBLE);
            circle_label.setVisibility(View.VISIBLE);
            line_abs_label.setVisibility(View.VISIBLE);
            line_ord_label.setVisibility(View.VISIBLE);
            graph.setVisibility(View.VISIBLE);
            graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE); // grille et axe invisible
            graph.getGridLabelRenderer().setVerticalLabelsVisible(false); //label ordonnée invisible
            graph.getGridLabelRenderer().setHorizontalLabelsVisible(false); //label abscisse invisible
            ConnectedThread connectThread = new ConnectedThread(btSocket);
            connectThread.start();
        }
    };

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        DataInputStream in ;
        InputStreamReader aReader;
        BufferedReader mBufferedReader;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
                in = new DataInputStream(tmpIn);
                aReader = new InputStreamReader(in);
                mBufferedReader = new BufferedReader(aReader);

            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            // Keep reading the BufferedReader until an exception occurs.
            while (true) {
                try {
                    String line = mBufferedReader.readLine();
                    String msgBarycentreX = line.substring(line.indexOf("[BX]") + 4, line.indexOf("[BY]"));
                    String msgBarycentreY = line.substring(line.indexOf("[BY]") + 4);
                    Log.d("MSG_DEBUG", "MESSAGE RECU :" + msgBarycentreX);
                    Log.d("MSG_DEBUG", "MESSAGE RECU :" + msgBarycentreY);
                    barycentreX.setText(msgBarycentreX);
                    barycentreY.setText(msgBarycentreY);
                    affichagePoint();
                } catch (IOException e) {
                    Log.d(TAG, "Disconnected");
                    toast.show();
                    break;
                }
            }
        }

        // Call this from the main activity to send data to the remote device.
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
            }
        }

        // Call this method from an activity to shut down the connection.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }

        public void affichagePoint() {
            double x = Double.parseDouble(String.valueOf(barycentreX.getText()));
            double y = Double.parseDouble(String.valueOf(barycentreY.getText()));
            graph.removeAllSeries(); // suppression du point
            PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(new DataPoint[]{
                    new DataPoint(x, y)});
            series.setSize(25);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(2);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(2);
            graph.getViewport().setYAxisBoundsManual(true); // réglage des axes entre 0 et 2
            graph.getViewport().setXAxisBoundsManual(true);
            graph.addSeries(series);
        }
    }
}