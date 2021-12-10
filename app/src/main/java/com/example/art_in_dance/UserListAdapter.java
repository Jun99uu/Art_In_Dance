package com.example.art_in_dance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private ArrayList<UserListItem> items = null;
    private Context context;

    UserListAdapter(ArrayList<UserListItem> items, Context context){
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.user_item, parent, false);
        UserListAdapter.ViewHolder vh = new UserListAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserListAdapter.ViewHolder vh = (UserListAdapter.ViewHolder)holder;
        UserListItem item = items.get(position);
        vh.item.setText("이름 : " + item.getName() + "\n아이디 : "+ item.getID() + "\n생년월일 : " + item.getBD());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle("삭제하시겠습니까?"); //제목
                dlg.setMessage("해당 회원의 예약을 정말로 취소하시겠습니까?"); // 메시지
                dlg.setIcon(R.drawable.bomb); // 아이콘 설정

                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        DeleteClassRequest deleteClassRequest = new DeleteClassRequest(item.getID(), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(deleteClassRequest);

                        Response.Listener<String> responseListenersecond = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObjectsecond = new JSONObject(response);
                                    boolean success2 = jsonObjectsecond.getBoolean("success");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        MinusPersonRequest minusPersonRequest = new MinusPersonRequest(item.getCURRENT(), item.getDate(), responseListenersecond);
                        RequestQueue queuesecond = Volley.newRequestQueue(context);
                        queuesecond.add(minusPersonRequest);
                        Toast.makeText(context,String.format("해당 회원의 예약이 취소되었습니다.\n 새로고침해주세요."), Toast.LENGTH_SHORT).show();
                    }
                });

                dlg.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.useritem);
        }
    }
}
