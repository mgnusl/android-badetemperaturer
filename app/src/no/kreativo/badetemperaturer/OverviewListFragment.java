package no.kreativo.badetemperaturer;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.applidium.headerlistview.HeaderListView;
import no.kreativo.badetemperaturer.adapter.OverviewListAdapter;
import no.kreativo.badetemperaturer.data.County;

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

        HeaderListView listView = (HeaderListView) view.findViewById(R.id.overviewListView);

        listView.setAdapter(new OverviewListAdapter(getActivity(), listOfCounties));

        ListView lv = listView.getListView();
        lv.setDivider(new ColorDrawable(getResources().getColor(R.color.list_divider)));
        lv.setDividerHeight(1);


        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
