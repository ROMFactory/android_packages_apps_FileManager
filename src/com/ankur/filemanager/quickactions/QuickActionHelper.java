package com.ankur.filemanager.quickactions;

import java.io.File;

import com.ankur.filemanager.R;
import com.ankur.filemanager.activity.FileListActivity;
import com.ankur.filemanager.model.FileListEntry;
import com.ankur.filemanager.util.FileActionsHelper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;

public final class QuickActionHelper {


	private static QuickActionHelper instance;
	
	private FileListActivity mContext;
	private boolean showActions = true;
	
	public static synchronized QuickActionHelper get(FileListActivity context)
	{
		if(instance == null)
		{
			instance = new QuickActionHelper(context);
		}
		return instance;
	}
	
	private QuickActionHelper(FileListActivity mContext) {
		super();
		this.mContext = mContext;
	}


	 public void showQuickActions(final ImageView view, final FileListEntry entry) {
		
		if(!showActions)
		{
			return;
		}
		final File file = entry.getPath();
		final QuickAction actions = new QuickAction(view);
		int[] availableActions = FileActionsHelper.getContextMenuOptions(file, mContext);
		view.setImageDrawable(mContext.getResources().getDrawable(R.drawable.list_actions_blu));
		
		ActionItem action = null;
		
		for(int i=0;i<availableActions.length;i++)
		{
			int a = availableActions[i];
			action = null;
			switch (a) {
			case R.id.menu_cut:
				action = new ActionItem(mContext.getResources().getDrawable(R.drawable.action_cut));
				action.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						FileActionsHelper.cutFile(file, mContext);
						actions.dismiss();
					}
				});
				break;
				
			case R.id.menu_copy:
				action  = new ActionItem(mContext.getResources().getDrawable(R.drawable.action_copy));
				action.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						FileActionsHelper.copyFile(file, mContext);
						actions.dismiss();
					}
				});
				break;
				
			case R.id.menu_delete:
				action  = new ActionItem(mContext.getResources().getDrawable(R.drawable.action_delete));
				 
				action.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(final View v) {
						
						actions.dismiss();
						FileActionsHelper.deleteFile(file, mContext, null);
						
					}
				});
				break;
				
			case R.id.menu_share:
				action = new ActionItem(mContext.getResources().getDrawable(R.drawable.action_share));
				action.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						
						actions.dismiss();
						FileActionsHelper.share(file, mContext);
						
						
					}
				});

				break;
				
			case R.id.menu_zip:
				action = new ActionItem(mContext.getResources().getDrawable(R.drawable.action_zip));
				action.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						
						actions.dismiss();
						FileActionsHelper.zip(file, mContext);
						
					}
				});

				break;

			default:
				break;
			}
			
			if(action!=null)
			{
				 actions.addActionItem(action);
			}
		}
		

		if(availableActions.length>0)
		{
			actions.setAnimStyle(QuickAction.ANIM_AUTO);
			actions.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
					view.setImageDrawable(mContext.getResources().getDrawable(R.drawable.list_actions));
					
				}
			});
			actions.show();
		}
		 
	 }

	public boolean isShowActions() {
		return showActions;
	}

	public void setShowActions(boolean showActions) {
		this.showActions = showActions;
	}

}
