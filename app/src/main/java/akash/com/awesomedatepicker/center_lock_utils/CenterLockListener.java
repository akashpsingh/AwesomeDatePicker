package akash.com.awesomedatepicker.center_lock_utils;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import akash.com.awesomedatepicker.R;

public class CenterLockListener extends RecyclerView.OnScrollListener {

    private LockListener mCallback;
    boolean autoSet = true;//To avoid recursive calls
    int position;
    int SCREEN_CENTER;
    View view;


    public  CenterLockListener(int center, LockListener listner){

        mCallback = listner;
        SCREEN_CENTER=center;

    }
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        LinearLayoutManager lm= (LinearLayoutManager) recyclerView.getLayoutManager();
        if(SCREEN_CENTER==0){
            SCREEN_CENTER =(lm.getOrientation()==LinearLayoutManager.HORIZONTAL?(recyclerView.getLeft()+recyclerView.getRight()) :(recyclerView.getTop()+recyclerView.getBottom()));
        }

        if(!autoSet) {

            if(newState==RecyclerView.SCROLL_STATE_IDLE) {
                //ScrollStoppped
                view=findCenterView(lm);//get the view nearest to center
                int center= lm.getOrientation()==LinearLayoutManager.HORIZONTAL? (view.getLeft()+view.getRight())/2 :(view.getTop()+view.getBottom())/2;
                int scrollNeeded= center-SCREEN_CENTER;//compute scroll from center
                if(lm.getOrientation()==LinearLayoutManager.HORIZONTAL)
                    recyclerView.smoothScrollBy(scrollNeeded,0);
                else {
                    recyclerView.smoothScrollBy(0, (int) (scrollNeeded));
                }
                autoSet=true;
                if(mCallback != null) {
                    mCallback.handlePositionChanged(position, recyclerView.getId());
                    //mCallback.makeListsVisible();
                }
            }
        }

        if(newState==RecyclerView.SCROLL_STATE_DRAGGING || newState==RecyclerView.SCROLL_STATE_SETTLING){
            autoSet=false;

            //hide other list when scrolling
            //mCallback.hideOtherLists(recyclerView.getId());
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

    }
    private View findCenterView(LinearLayoutManager lm) {
        position = -1;
        int mindist = 0;
        View view = null, retview = null;
        boolean notfound = true;
        for(int i = lm.findFirstVisibleItemPosition(); i <= lm.findLastVisibleItemPosition() && notfound; i++){
            view = lm.findViewByPosition(i);
            int center = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? (view.getLeft() + view.getRight())/2 :(view.getTop() + view.getBottom())/2;
            int leastdiff = Math.abs(SCREEN_CENTER - center);
            if(leastdiff <= mindist || i == lm.findFirstVisibleItemPosition()) {
                mindist = leastdiff;
                retview = view;
                position = i;
            } else
            {
                notfound = false;
            }
        }

        return retview;
    }
}