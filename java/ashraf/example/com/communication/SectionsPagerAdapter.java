package ashraf.example.com.communication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ashraf on 3/13/2018.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter{


    public
    SectionsPagerAdapter(FragmentManager fm) {
        super( fm );
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                ChatsFragment ChatsFragment=new ChatsFragment();
                return ChatsFragment;
            case 1:
                FriendsFragment FriendsFragment=new FriendsFragment();
                return FriendsFragment;
                default :
                    return null;
        }

    }

    @Override
    public
    int getCount() {
        return 2;
    }
    public CharSequence getPageTitle(int position){
        switch(position ) {

            case 0:
                return "Chats";
            case 1:
                return "Friends";
                default:
                    return null;
        }
        }



    }

