package no.kreativo.badevann;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.applidium.headerlistview.HeaderListView;
import no.kreativo.badevann.adapter.OverviewListAdapter;
import no.kreativo.badevann.data.County;

import java.util.ArrayList;

public class OverviewListFragment extends Fragment {

    private ArrayList<County> listOfCounties;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overview_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listOfCounties = bundle.getParcelableArrayList("counties");
        }

        HeaderListView listView = (HeaderListView)view.findViewById(R.id.overviewListView);

        listView.setAdapter(new OverviewListAdapter(getActivity(), listOfCounties));


        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}