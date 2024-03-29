package com.delegateadapters.delegate.diff;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.delegateadapters.delegate.CompositeDelegateAdapter;
import com.delegateadapters.delegate.IDelegateAdapter;

import java.util.List;


public class DiffUtilCompositeAdapter extends CompositeDelegateAdapter<IComparableItem> {

    protected DiffUtilCompositeAdapter(@NonNull SparseArray<IDelegateAdapter> typeToAdapterMap) {
        super(typeToAdapterMap);
    }

    @Override
    public void swapData(@NonNull List<IComparableItem> data) {
        final DiffUtilCallback diffCallback = new DiffUtilCallback(this.data, data);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.data.clear();
        this.data.addAll(data);
        diffResult.dispatchUpdatesTo(this);
    }


    public static class Builder {

        private int count;
        private final SparseArray<IDelegateAdapter> typeToAdapterMap;

        public Builder() {
            typeToAdapterMap = new SparseArray<>();
        }

        public Builder add(
            @NonNull IDelegateAdapter<?, ? extends IComparableItem> delegateAdapter) {
            typeToAdapterMap.put(count++, delegateAdapter);
            return this;
        }

        public DiffUtilCompositeAdapter build() {
            if (count == 0) throw new IllegalArgumentException("Register at least one adapter");
            return new DiffUtilCompositeAdapter(typeToAdapterMap);
        }
    }
}
