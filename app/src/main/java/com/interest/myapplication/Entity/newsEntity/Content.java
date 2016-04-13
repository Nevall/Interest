package com.interest.myapplication.Entity.newsEntity;

import com.interest.myapplication.Entity.SaveEntity;

import java.io.Serializable;
import java.util.List;

/**
 * ���ž�������ʵ����
 * @author Android
 */
public class Content extends SaveEntity implements Serializable{
	
	private int id;
    private List<RecommendersEntity> recommenders;
    private String body;
    private String title;
    private String ga_prefix;
    private String share_url;
    private String image;
    private int type;
    private List<String> css;
    private String image_source;
	public Content() {
		super();
	}
	public Content(int id, List<RecommendersEntity> recommenders, String body,
			String title, String ga_prefix, String share_url, String image,
			int type, List<String> css, String image_source) {
		super();
		this.id = id;
		this.recommenders = recommenders;
		this.body = body;
		this.title = title;
		this.ga_prefix = ga_prefix;
		this.share_url = share_url;
		this.image = image;
		this.type = type;
		this.css = css;
		this.image_source = image_source;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<RecommendersEntity> getRecommenders() {
		return recommenders;
	}
	public void setRecommenders(List<RecommendersEntity> recommenders) {
		this.recommenders = recommenders;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGa_prefix() {
		return ga_prefix;
	}
	public void setGa_prefix(String ga_prefix) {
		this.ga_prefix = ga_prefix;
	}
	public String getShare_url() {
		return share_url;
	}
	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<String> getCss() {
		return css;
	}
	public void setCss(List<String> css) {
		this.css = css;
	}
	public String getImage_source() {
		return image_source;
	}
	public void setImage_source(String image_source) {
		this.image_source = image_source;
	}
	
	@Override
	public String toString() {
		return "Content [id=" + id + ", recommenders=" + recommenders
				+ ", body=" + body + ", title=" + title + ", ga_prefix="
				+ ga_prefix + ", share_url=" + share_url + ", image=" + image
				+ ", type=" + type + ", css=" + css + ", image_source="
				+ image_source + "]";
	}
}
