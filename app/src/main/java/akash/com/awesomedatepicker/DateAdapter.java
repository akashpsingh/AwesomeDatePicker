package akash.com.awesomedatepicker;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by akashsingh on 10/10/15.
 */
public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    private List<DatePickerFragment.CustomListItem> mData;
    private Context mContext;

    public DateAdapter(List<DatePickerFragment.CustomListItem> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_date_picker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mContent.setText(mData.get(position).getValue());

        if(mData.get(position).isSelected()) {

            holder.mContent.setTextSize(24);
            holder.mContent.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.mContent.setTypeface(null, Typeface.BOLD);

        } else {

            holder.mContent.setTextSize(20);
            holder.mContent.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.mContent.setTypeface(null, Typeface.NORMAL);

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mContent;

        public ViewHolder(View itemView) {
            super(itemView);

            mContent = (TextView)itemView.findViewById(R.id.list_item_content);
        }
    }
}
