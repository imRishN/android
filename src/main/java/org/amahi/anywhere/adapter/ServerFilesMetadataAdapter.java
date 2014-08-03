/*
 * Copyright (c) 2014 Amahi
 *
 * This file is part of Amahi.
 *
 * Amahi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Amahi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Amahi. If not, see <http ://www.gnu.org/licenses/>.
 */

package org.amahi.anywhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.amahi.anywhere.R;
import org.amahi.anywhere.server.model.ServerFile;
import org.amahi.anywhere.server.model.ServerFileMetadata;

import java.util.Collections;
import java.util.List;

public class ServerFilesMetadataAdapter extends BaseAdapter
{
	private final Context context;
	private final LayoutInflater layoutInflater;

	private List<ServerFile> files;
	private List<ServerFileMetadata> filesMetadata;

	public ServerFilesMetadataAdapter(Context context) {
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);

		this.files = Collections.emptyList();
		this.filesMetadata = Collections.emptyList();
	}

	public void replaceWith(List<ServerFile> files, List<ServerFileMetadata> filesMetadata) {
		this.files = files;
		this.filesMetadata = filesMetadata;

		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return files.size();
	}

	public List<ServerFile> getItems() {
		return files;
	}

	public List<ServerFileMetadata> getExtraItems() {
		return filesMetadata;
	}

	@Override
	public ServerFile getItem(int position) {
		return files.get(position);
	}

	public ServerFileMetadata getExtraItem(int position) {
		return filesMetadata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup container) {
		ServerFile file = getItem(position);
		ServerFileMetadata fileMetadata = getExtraItem(position);

		if (view == null) {
			view = newView(container);
		}

		bindView(file, fileMetadata, view);

		return view;
	}

	private View newView(ViewGroup container) {
		return layoutInflater.inflate(R.layout.view_server_file_metadata_item, container, false);
	}

	private void bindView(ServerFile file, ServerFileMetadata fileMetadata, View fileView) {
		if (fileMetadata == null) {
			bindFileView(file, fileView);
		} else {
			bindFileMetadataView(fileMetadata, fileView);
		}
	}

	private void bindFileView(ServerFile file, View fileView) {
		ImageView fileIconView = (ImageView) fileView.findViewById(R.id.icon);
		TextView fileTextView = (TextView) fileView.findViewById(R.id.text);

		fileIconView.setImageResource(R.drawable.ic_file_video);
		fileTextView.setText(file.getName());
	}

	private void bindFileMetadataView(ServerFileMetadata fileMetadata, View fileView) {
		ImageView fileIconView = (ImageView) fileView.findViewById(R.id.icon);
		TextView fileTextView = (TextView) fileView.findViewById(R.id.text);

		Picasso.with(context)
			.load(fileMetadata.getArtworkUrl())
			.centerCrop()
			.fit()
			.into(fileIconView);

		fileTextView.setText(fileMetadata.getTitle());
	}
}
