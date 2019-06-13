package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uniba.di.itps.ciceroneapp.GestioneAttività.adapter.TabsEventAdapter;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView.MyEventCreatedFragment;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView.TabRequestedFragment;
import uniba.di.itps.ciceroneapp.R;



public class EventFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event,container,false);
        ViewPager viewPager = view.findViewById(R.id.eventViewPage);
        setupViewPager(viewPager);
        TabLayout tabs = view.findViewById(R.id.eventTabLayout);
        tabs.setupWithViewPager(viewPager);
        return view;

    }


    private void setupViewPager(ViewPager viewPager) {


        TabsEventAdapter adapter = new TabsEventAdapter(getChildFragmentManager());
        adapter.addFragment(new MyEventCreatedFragment(), getContext().getResources().getString(R.string.createdTab));
        adapter.addFragment(new TabRequestedFragment(), getContext().getResources().getString(R.string.requestedTab));
        viewPager.setAdapter(adapter);
    }


        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
