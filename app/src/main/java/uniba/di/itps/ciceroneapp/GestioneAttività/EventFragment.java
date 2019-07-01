package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uniba.di.itps.ciceroneapp.GestioneAttività.adapter.TabsEventAdapter;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView.MyEventCreatedFragment;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView.MyEventCreatedFragmentPast;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView.MyEventRequestedPast;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView.TabRequestedFragment;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.manageProfile.ProfileMainFragment;


public class EventFragment extends Fragment {
    InterfaceGestioneAttività.Presenter presenter;
    Toolbar indietro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event,container,false);
        ViewPager viewPager = view.findViewById(R.id.eventViewPage);
        presenter = new PresenterGestioneAttività(getActivity());
        indietro = view.findViewById(R.id.storico);
        setupViewPager(viewPager);
        TabLayout tabs = view.findViewById(R.id.eventTabLayout);
        indietro.setNavigationOnClickListener(v ->
                presenter.addFragment(new ProfileMainFragment(), (InterfaceGestioneAttività.MvpView) getActivity()));
        tabs.setupWithViewPager(viewPager);
        return view;

    }


    private void setupViewPager(ViewPager viewPager) {


        TabsEventAdapter adapter = new TabsEventAdapter(getChildFragmentManager());
        Bundle b = getArguments();
        if(b == null){
        adapter.addFragment(new MyEventCreatedFragment(), getContext().getResources().getString(R.string.createdTab));
        adapter.addFragment(new TabRequestedFragment(), getContext().getResources().getString(R.string.requestedTab));}
        else{
            indietro.setVisibility(View.VISIBLE);
            adapter.addFragment(new MyEventCreatedFragmentPast(),"Storico Create");
            adapter.addFragment(new MyEventRequestedPast(),"Storico Richieste");
        }
        viewPager.setAdapter(adapter);
    }


        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



}
