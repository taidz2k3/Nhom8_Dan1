package com.thondph16247.nhom8.Adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.thondph16247.nhom8.DAO.GioHangDAO;
import com.thondph16247.nhom8.DAO.SanPhamDAO;
import com.thondph16247.nhom8.DTO.GioHangDTO;
import com.thondph16247.nhom8.DTO.LoaiTraiCayDTO;
import com.thondph16247.nhom8.R;

import java.util.ArrayList;

public class GioHangAdapter extends  RecyclerView.Adapter<GioHangAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<GioHangDTO> mList;





    public GioHangAdapter(Context context, ArrayList<GioHangDTO> list) {
        this.mContext = context;
        this.mList = list;
    }
    public void updateData(ArrayList<GioHangDTO> newData) {
        mList.clear();
        mList.addAll(newData);
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_gio_hang, parent, false);
        return new GioHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHangDTO gioHangDTO = mList.get(position);

        holder.txtTenSP.setText("Tên sản phẩm: " + gioHangDTO.getTenSP());
        holder.txtGiaTien.setText("Thành tiền: " + gioHangDTO.getGiaTienMoi()); // Sử dụng giaTienMoi thay vì giaTien
        holder.txtSoLuong.setText("Số lượng: " + gioHangDTO.getSoLuongGioHang());

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Cảnh báo").setMessage("Bạn có muốn xóa sản phẩm này ?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GioHangDAO dao = new GioHangDAO(mContext);
                        int kq = dao.XoaGioHang(gioHangDTO);
                        if (kq > 0) {
                            mList.clear();
                            mList.addAll(dao.getList());
                            notifyDataSetChanged();
                            Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                        } else {
                            Toast.makeText(mContext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSP, txtGiaTien, txtSoLuong;
        ImageView img_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSP = itemView.findViewById(R.id.txt_tenSP_gioHang);
            txtGiaTien = itemView.findViewById(R.id.txt_giaTien_gioHang);
            txtSoLuong = itemView.findViewById(R.id.txt_soLuong_gioHang);
            img_delete = itemView.findViewById(R.id.img_xoa_gioHang);
        }
    }

}
