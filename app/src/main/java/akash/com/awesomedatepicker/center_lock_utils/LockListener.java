package akash.com.awesomedatepicker.center_lock_utils;

/**
 * Created by akashsingh on 10/10/15.
 */
public interface LockListener {

    void handlePositionChanged(int pos, int id);

    void hideOtherLists(int scrollingListId);

    void makeListsVisible();
}
