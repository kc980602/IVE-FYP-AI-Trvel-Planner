package com.triple.triple.Presenter.Attraction;


import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.triple.triple.Adapter.AttractionListAdapter;
import com.triple.triple.Helper.RecycleViewPaddingHelper;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.R;

import java.util.ArrayList;
import java.util.List;


public class AttractionFragment extends Fragment implements SearchView.OnQueryTextListener{


    private RecyclerView rv_attraction;
    private AttractionListAdapter adapter;
    private DataMeta dataMeta;
    SearchView searchView;

    public AttractionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attraction, container, false);
        rv_attraction = (RecyclerView) view.findViewById(R.id.rv_attraction);
        dataMeta = (DataMeta) getArguments().getSerializable("dataMeta");
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
    }

    private void initView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        adapter = new AttractionListAdapter(getActivity(), dataMeta);
        rv_attraction.setHasFixedSize(true);
        rv_attraction.setLayoutManager(mLayoutManager);
        rv_attraction.setItemAnimator(new DefaultItemAnimator());
        rv_attraction.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecyclerView.ItemDecoration dividerItemDecoration = new RecycleViewPaddingHelper(90);
        rv_attraction.addItemDecoration(dividerItemDecoration);

    }
//    public void beginSearch(String query) {
//        adapter.getFilter().filter(query);
//    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.toolbar_main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(getActivity().SEARCH_SERVICE);
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);


        if (mSearchMenuItem != null) {
            searchView = (SearchView) mSearchMenuItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        searchView.setIconifiedByDefault(true);
        MenuItemCompat.expandActionView(mSearchMenuItem);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_search || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        final List<Attraction> filteredModelList = filter(dataMeta.getAttractions(), s);

        adapter.setFilter(filteredModelList);
        return true;

    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private List<Attraction> filter(List<Attraction> attractionList, String query) {
        query = query.toLowerCase();
        final List<Attraction> filteredAttractionList = new ArrayList<>();
        for (Attraction attraction : attractionList) {
            final String text = attraction.getName().toLowerCase();
            if (text.contains(query)) {
                filteredAttractionList.add(attraction);
            }
        }
        return filteredAttractionList;
    }
}