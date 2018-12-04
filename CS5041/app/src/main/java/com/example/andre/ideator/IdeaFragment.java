package com.example.andre.ideator;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import java.lang.reflect.Array;
import java.util.ArrayList;



public class IdeaFragment extends DialogFragment {


    public static IdeaFragment newInstance(ArrayList<Idea> ideas, int position){
        IdeaFragment frag = new IdeaFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("ideas", ideas);
        args.putInt("position", position);
        frag.setArguments(args);

        return frag;
    }
    private OnFragmentInteractionListener mListener;


    //https://developer.android.com/guide/topics/ui/dialogs#java
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View l = inflater.inflate(R.layout.dialog_layout, null);
        final ArrayList<Idea> ideas = getArguments().getParcelableArrayList("ideas");
        final int pos = getArguments().getInt("position");


        builder.setMessage("Change Description or Choose Color")
                .setView(l)
                .setTitle("Edit Idea")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final EditText desc = (EditText) l.findViewById(R.id.description);
                        final EditText title = (EditText) l.findViewById(R.id.title);
                        EditText color = (EditText) l.findViewById(R.id.color);
                        int[] colors = getResources().getIntArray(R.array.colors);
                        switch(color.getText().toString()){

                            case "1":
                                ideas.get(pos).setColor(colors[0]);

                                break;
                            case "2":

                                ideas.get(pos).setColor(colors[1]);
                                break;

                            case "3":

                                ideas.get(pos).setColor(colors[2]);
                                break;

                            case "4":

                                ideas.get(pos).setColor(colors[3]);
                                break;

                            case "5":

                                ideas.get(pos).setColor(colors[4]);
                                break;

                            case "6":

                                ideas.get(pos).setColor(colors[5]);
                                break;

                            case "7":

                                ideas.get(pos).setColor(colors[6]);
                                break;

                            case "8":

                                ideas.get(pos).setColor(colors[7]);
                                break;

                            case "9":

                                ideas.get(pos).setColor(colors[8]);
                                break;

                            case "10":

                                ideas.get(pos).setColor(colors[9]);
                                break;

                            case "11":

                                ideas.get(pos).setColor(colors[10]);
                                break;

                            case "12":

                                ideas.get(pos).setColor(colors[11]);
                                break;
                        }
                        if(desc.getText().toString().length() >= 1){
                            ideas.get(pos).setDesc(desc.getText().toString());
                        }
                        if(title.getText().toString().length() >= 1){
                            ideas.get(pos).setTitle(title.getText().toString());
                        }

                        desc.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                // Fires right as the text is being changed (even supplies the range of text)
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                          int after) {
                                // Fires right before text is changing
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                // Fires right after the text has changed
                                ideas.get(pos).setDesc(desc.getText().toString());
                            }
                        });
                        title.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                // Fires right as the text is being changed (even supplies the range of text)
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                          int after) {
                                // Fires right before text is changing
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                // Fires right after the text has changed
                                ideas.get(pos).setTitle(title.getText().toString());
                                System.out.println("s: " + s.toString());
                            }
                        });




                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        IdeaFragment.this.getDialog().cancel();
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        ideas.remove(pos);

                    }
                });
        // Create the AlertDialog object and return it

        return builder.create();
    }



    public IdeaFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idea, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
