package com.lsqidsd.hodgepodge.adapter;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.ViewHolder.LoadMoreHolder;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.bean.Milite;
import com.lsqidsd.hodgepodge.databinding.Loadbinding;
import com.lsqidsd.hodgepodge.databinding.NewsItemHotBinding;
import com.lsqidsd.hodgepodge.viewmodel.HotViewModule;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.util.HashMap;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Milite.DataBean> milites;
    private Context context;
    private int page;
    private GridViewImgAdapter gridViewImgAdapter;
    private LayoutInflater inflate;
    private final int LOAD_MORE = -1;//上拉加载
    private final int NEWS_ITEM_TYPE = 1;//列表
    private String categorie;
    private HashMap<String, String> params;
    private RefreshLayout refreshLayout;

    public CategoriesAdapter(Context context, RefreshLayout refreshLayout) {
        this.context = context;
        this.refreshLayout = refreshLayout;
        this.inflate = LayoutInflater.from(context);

    }

    public void addMilite(List<Milite.DataBean> milite, String s) {
        this.milites = milite;
        this.categorie = s;
        this.page = 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        NewsItemHotBinding newsItemHotBinding;
        Loadbinding loadbinding;
        switch (viewType) {
            case NEWS_ITEM_TYPE:
                newsItemHotBinding = DataBindingUtil.inflate(inflate, R.layout.news_item_hot, parent, false);
                viewHolder = new CategoriesViewHolder(newsItemHotBinding);
                break;
            case LOAD_MORE:
                loadbinding = DataBindingUtil.inflate(inflate, R.layout.loadmore, parent, false);
                viewHolder = new LoadMoreHolder(loadbinding);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CategoriesViewHolder) {
            CategoriesViewHolder categoriesViewHolder = (CategoriesViewHolder) holder;
            categoriesViewHolder.loadData(milites.get(position));
        }
        if (holder instanceof LoadMoreHolder) {
            if (categorie.equals("ent")) {
                params = BaseConstant.getEntParams();
            }
            if (categorie.equals("milite")) {
                params = BaseConstant.getMiliteParams();
            }
            if (categorie.equals("world")) {
                params = BaseConstant.getWorldParams();
            }
            if (categorie.equals("finance")) {
                params = BaseConstant.getFinanceParams();
            }
            if (categorie.equals("rec")) {
                params = BaseConstant.getRecommend();
            }
            if (categorie.equals("history")) {
                params = BaseConstant.getHistory();
            }
            if (categorie.equals("cul")) {
                params = BaseConstant.getCul();
            }
            refreshLayout.setOnLoadMoreListener(a -> HttpModel.getCategoriesNews(page,context, params, (b, c) -> page++, milites, refreshLayout, ""));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return LOAD_MORE;
        } else {
            return NEWS_ITEM_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return milites.size() + 1;
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {
        NewsItemHotBinding itemHotBinding;

        public CategoriesViewHolder(NewsItemHotBinding itemView) {
            super(itemView.getRoot());
            itemHotBinding = itemView;
        }

        private void loadData(Milite.DataBean bean) {
            if (bean.getIrs_imgs().get_$227X148() != null && bean.getIrs_imgs().get_$227X148().size() == 3) {
                gridViewImgAdapter = new GridViewImgAdapter(context);
                gridViewImgAdapter.addImgs(bean.getIrs_imgs().get_$227X148(), bean.getUrl(), itemHotBinding);
                itemHotBinding.gv.setAdapter(gridViewImgAdapter);
            }

            itemHotBinding.setNewsitem(new HotViewModule(context, bean.getIrs_imgs().get_$227X148(), bean, itemHotBinding));
        }
    }
}
