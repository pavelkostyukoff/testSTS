package com.delegateadapters.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseDelegateAdapter
        <VH extends BaseViewHolder, T> implements IDelegateAdapter<VH, T> {

    abstract protected void onBindViewHolder(@NonNull View view,
                                             @NonNull T item, @NonNull VH viewHolder);

    @LayoutRes
    abstract protected int getLayoutId();

    /**
     * @param parent inflated view
     */
    @NonNull
    abstract protected VH createViewHolder(View parent);

    @Override
    public void onRecycled(@NonNull VH holder) {
    }

    @NonNull
    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {
        final View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(getLayoutId(), parent, false);
        final VH holder = createViewHolder(inflatedView);
        holder.setListener(new BaseViewHolder.ItemInflateListener() {
            @Override
            public void inflated(Object viewType, View view) {
                onBindViewHolder(view, (T) viewType, holder);
            }
        });
        return holder;
    }

    @Override
    public final void onBindViewHolder(
            @NonNull VH holder,
            @NonNull List<T> items,
            int position) {
        holder.bind(items.get(position));
    }
}
