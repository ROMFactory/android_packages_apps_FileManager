package com.ankur.filemanager.adapters;

import java.util.List;

import com.ankur.filemanager.FileExplorerApp;
import com.ankur.filemanager.R;
import com.ankur.filemanager.activity.FileListActivity;
import com.ankur.filemanager.model.FileListEntry;
import com.ankur.filemanager.quickactions.QuickActionHelper;
import com.ankur.filemanager.util.Util;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileListAdapter extends BaseAdapter {

	public static class ViewHolder 
	{
	  public TextView resName;
	  public ImageView resIcon;
	  public ImageView resActions;
	  public TextView resMeta;
	}

	private static final String TAG = FileListAdapter.class.getName();
	  
	private FileListActivity mContext;
	private List<FileListEntry> files;
	private LayoutInflater mInflater;
	
	public FileListAdapter(FileListActivity context, List<FileListEntry> files) {
		super();
		mContext = context;
		this.files = files;
		mInflater = mContext.getLayoutInflater();
		
	}

	
	@Override
	public int getCount() {
		if(files == null)
		{
			return 0;
		}
		else
		{
			return files.size();
		}
	}

	@Override
	public Object getItem(int arg0) {

		if(files == null)
			return null;
		else
			return files.get(arg0);
	}

	public List<FileListEntry> getItems()
	{
	  return files;
	}
	
	@Override
	public long getItemId(int position) {

		return position;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
        if (convertView == null) 
        {
        	if(FileExplorerApp.THEME_BLACK == mContext.getPreferenceHelper().getTheme())
        	{
        		convertView = mInflater.inflate(R.layout.explorer_item, parent, false);	
        	}
        	else
        	{
        		convertView = mInflater.inflate(R.layout.explorer_item_light, parent, false);
        	}
            holder = new ViewHolder();
            holder.resName = (TextView)convertView.findViewById(R.id.explorer_resName);
            holder.resMeta = (TextView)convertView.findViewById(R.id.explorer_resMeta);
            holder.resIcon = (ImageView)convertView.findViewById(R.id.explorer_resIcon);
            holder.resActions = (ImageView)convertView.findViewById(R.id.explorer_resActions);
            convertView.setTag(holder);
        } 
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        final FileListEntry currentFile = files.get(position);
        holder.resName.setText(currentFile.getName());
        holder.resIcon.setImageDrawable(Util.getIcon(mContext, currentFile.getPath()));
        String meta = Util.prepareMeta(currentFile, mContext);
        holder.resMeta.setText(meta);
        if(!Util.canShowQuickActions(currentFile, mContext))
        {
        	holder.resActions.setVisibility(View.INVISIBLE);
        }
        else
        {
        	holder.resActions.setVisibility(View.VISIBLE);
        	holder.resActions.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					QuickActionHelper helper = QuickActionHelper.get(mContext);
					helper.showQuickActions((ImageView)v, currentFile);

				}
			});
        }
        
        return convertView;
	}

}
