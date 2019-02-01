package com.yaoguang.lib.appcommon.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.lib.R;
import com.yaoguang.lib.adapter.AllSelectAdapter;
import com.yaoguang.lib.common.ConvertUtils;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.UiUtils;

import java.util.HashSet;
import java.util.List;

/**
 * 作    者：韦理英
 * 时    间：2017/9/5 0005.
 * 描    述：通用的全选删除
 * <p>
 * update
 * zhongjh
 */
public class AllSelectDelete implements Toolbar.OnMenuItemClickListener {
    public HashSet<String> selectDeleteIds = new HashSet<>();
    public HashSet<Integer> selectDeletePos = new HashSet<>();

    public boolean isEdit;  // 是否编辑
    public boolean isAllSelect;  // 是否选择，第一次点击编辑是全选状态

    private Toolbar mToolbar;
    private final TextView mTvSelect;
    private TextView mTvDelete;
    private LinearLayout mLlSelectDelete;

    private AllSelectAdapter mAdapter;

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // 首次进入编辑，初始化
        if (!isEdit) {
            isEdit = true;
            isAllSelect = false;
            mAdapter.setEdit(true);
            mAdapter.setAllSelect(false);

            // 取消显示
            mToolbar.getMenu().findItem(R.id.edit).setVisible(false);
            mToolbar.getMenu().findItem(R.id.cancel).setVisible(true);
            // 启动删除功能
            mTvDelete.setEnabled(true);

            mLlSelectDelete.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();

        } else { // 取消 ： 选择状态
            isEdit = false;
            isAllSelect = false;
            mAdapter.setEdit(false);
            mAdapter.setAllSelect(false);

            // 编辑显示
            mToolbar.getMenu().findItem(R.id.edit).setVisible(true);
            mToolbar.getMenu().findItem(R.id.cancel).setVisible(false);
            // 不启动删除功能
            mTvDelete.setEnabled(false);

            // 清除数据
            selectDeleteIds.clear();
            selectDeletePos.clear();

            mLlSelectDelete.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();

            mTvSelect.setText("全选");
            mTvDelete.setTextColor(UiUtils.getColor(R.color.red_primary_666));
        }
        return false;
    }

    public AllSelectDelete(AllSelectAdapter adapter, final TextView tvSelect, final TextView tvDelete, LinearLayout llSelectDelete, Toolbar toolbar, final Runnable runnable) {
        mToolbar = toolbar;
        mTvSelect = tvSelect;
        mTvDelete = tvDelete;
        mLlSelectDelete = llSelectDelete;
        mAdapter = adapter;

        mLlSelectDelete.setVisibility(View.GONE);
        toolbar.setOnMenuItemClickListener(this);


        toolbar.inflateMenu(R.menu.alldelete);
        // 编辑菜单
        mToolbar.getMenu().findItem(R.id.edit).setVisible(false);
        mToolbar.getMenu().findItem(R.id.cancel).setVisible(false);

        // 选择事件
        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 全选或反选
                if (isAllSelect) {  // 取消
                    isAllSelect = false;
                    mAdapter.setAllSelect(false);
                    tvSelect.setText("全选"); // 取消时，变全选
                    tvDelete.setTextColor(UiUtils.getColor(R.color.red_primary_666));
                    mTvDelete.setEnabled(false);
                    mTvDelete.setClickable(false);
                } else {            //  全选
                    isAllSelect = true;
                    mAdapter.setAllSelect(true);
                    tvSelect.setText("取消全选");  // 全选时，字体变取消选择
                    tvDelete.setTextColor(UiUtils.getColor(R.color.red_primary_333));
                    mTvDelete.setEnabled(true);
                    mTvDelete.setClickable(true);
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        // 删除事件
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDeleteMessages();
                runnable.run();
            }
        });
    }

    /**
     * 描    述：更新菜单
     * 作    者：韦理英
     * 时    间：
     */
    public void initMenu(List<?> List) {
        //  没有数据就隐藏【编辑】，有就显示
        if (List == null || List.isEmpty()) {
            mToolbar.getMenu().findItem(R.id.edit).setVisible(false);
            mToolbar.getMenu().findItem(R.id.cancel).setVisible(false);

            // 隐藏底部
            mLlSelectDelete.setVisibility(View.GONE);
        } else {
            mToolbar.getMenu().findItem(R.id.edit).setVisible(true);
            mToolbar.getMenu().findItem(R.id.cancel).setVisible(false);

            // 如果是编辑状态则显示底部
            if (isEdit)
                mLlSelectDelete.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 作    者：韦理英
     * 时    间：
     *
     * @param flSelect flView
     * @param llView
     * @param checkBox ivView
     * @param holder
     */
    public static void adappterSetting(final AllSelectAdapter adapter, final View flSelect, final LinearLayout llView, final CheckBox checkBox, final RecyclerView.ViewHolder holder) {
        final int _pos = holder.getAdapterPosition();

        // 进入全选状态
        if (adapter.isAllSelect) {
            flSelect.setVisibility(View.VISIBLE);
        } else {  // 反选状态
            flSelect.setVisibility(View.GONE);
        }

        // 进入编辑状态
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        if (adapter.isEdit) {
            params.setMargins(ConvertUtils.dp2px(40), 0, 0, 0);
            flSelect.setVisibility(View.VISIBLE);
        } else {
            params.setMargins(0, 0, 0, 0);
            flSelect.setVisibility(View.GONE);
        }
        llView.setLayoutParams(params);

        // 选择事件
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onRecyclerViewItemClickSelectListener.onItemBtnSelectClick(checkBox, adapter.getList().get(_pos), _pos);
            }
        };
        // 选择
        checkBox.setFocusable(false);
        checkBox.setClickable(false);
        flSelect.setOnClickListener(onClickListener);
        llView.setOnClickListener(onClickListener);

        // 初始化checkbox 状态
        if (adapter.isSelected.get(_pos) != null) {
            checkBox.setChecked((Boolean) adapter.isSelected.get(_pos));
        }

        // 进入全选状态
        if (adapter.isAllSelect) {
            checkBox.setChecked(true);
        } else {  // 反选状态
            checkBox.setChecked(false);
        }
    }

    /**
     * 提交删除
     */
    public void submitDeleteMessages() {
        if (isAllSelect) {
            selectDeleteIds.clear();
            selectDeletePos.clear();
        } else {

        }
    }

    /**
     * 描    述：删除消息成功
     * 作    者：韦理英
     * 时    间：
     */
    public void deleteMessageSuccess() {
        ULog.i("deleteMessageSuccess=" + selectDeletePos.size());
        if (isAllSelect) {  // 全选删除
            mAdapter.getList().clear();
        } else {  // 单个删除
            for (Integer selectPo : selectDeletePos) {
                try {
                    mAdapter.removeItem(selectPo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        mAdapter.isSelected.clear();
        selectDeleteIds.clear();
        selectDeletePos.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 选择item
     *
     * @param checkBox 是否是选择
     * @param id       删除的id
     * @param position id的位置
     */
    public void selectItem(CheckBox checkBox, String id, Integer position) {
        if (checkBox.isChecked()) {
            mAdapter.isSelected.put(position, false);
            checkBox.setChecked(false);
            selectDeleteIds.remove(id);
            selectDeletePos.remove(position);
        } else {
            checkBox.setChecked(true);
            mAdapter.isSelected.put(position, true);
            selectDeleteIds.add(id);
            selectDeletePos.add(position);
        }
        // 选择和不选择改变字体颜色
        if (selectDeleteIds.isEmpty()) {
            mTvDelete.setTextColor(UiUtils.getColor(R.color.red_primary_666));
        } else {
            mTvDelete.setTextColor(UiUtils.getColor(R.color.red_primary_333));
        }
    }


}
