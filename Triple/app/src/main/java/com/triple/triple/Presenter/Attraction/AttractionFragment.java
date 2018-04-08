package com.triple.triple.Presenter.Attraction;


import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.triple.triple.Adapter.AttractionListAdapter;
import com.triple.triple.Helper.RecycleViewPaddingHelper;
import com.triple.triple.Model.Attraction;
import com.triple.triple.Model.DataMeta;
import com.triple.triple.Model.Pagination;
import com.triple.triple.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


public class AttractionFragment extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView rv_attraction;
    private AttractionListAdapter adapter;
    private DataMeta dataMeta, displayList;
    SearchView searchView;
    private boolean isFinal = false;
    private RecyclerView.LayoutManager mLayoutManager;

    public static final int PAGE_SIZE = 10;
    private int lastShowItem;

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
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        displayList = new DataMeta();
        displayList.setAttractions(dataMeta.getAttractions().subList(0, dataMeta.getAttractions().size() < PAGE_SIZE ? dataMeta.getAttractions().size() : PAGE_SIZE  ));
        adapter = new AttractionListAdapter(getActivity(), displayList);
        lastShowItem = displayList.getAttractions().size();
        rv_attraction.setHasFixedSize(true);
        rv_attraction.setLayoutManager(mLayoutManager);
        rv_attraction.setItemAnimator(new DefaultItemAnimator());
        rv_attraction.setAdapter(adapter);
        rv_attraction.addOnScrollListener(recyclerViewOnScrollListener);
        adapter.notifyDataSetChanged();
        RecyclerView.ItemDecoration dividerItemDecoration = new RecycleViewPaddingHelper(90);
        rv_attraction.addItemDecoration(dividerItemDecoration);

    }
//    public void beginSearch(String query) {
//        adapter.getFilter().filter(query);
//    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }


        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int totalCount = mLayoutManager.getItemCount();
            int visibleItemCount = mLayoutManager.getChildCount();
            int firstVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (!isFinal) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalCount
                        && firstVisibleItemPosition >= 0
                        && totalCount >= PAGE_SIZE) {
                    Log.e("Test","Test");
                    adapter.addAttraction(loadAttractions());
                }
            }
            adapter.notifyDataSetChanged();
        }

    };


    private List<Attraction> loadAttractions(){
        final List<Attraction> tempList = new ArrayList<Attraction>();
        int lastItem;
        if((lastShowItem + PAGE_SIZE) > dataMeta.getAttractions().size()){
            lastItem = dataMeta.getAttractions().size() - lastShowItem;
        } else {
            lastItem = lastShowItem + PAGE_SIZE;
        }
        for(int i = lastShowItem ; i < lastItem ; i++){
            if(i >= dataMeta.getAttractions().size()){
                isFinal = true;
            }
            tempList.add(dataMeta.getAttractions().get(i));
        }
        lastShowItem += PAGE_SIZE;
        return tempList;
    }

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