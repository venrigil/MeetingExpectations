package com.intera.roostrap.domain;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.intera.roostrap.domain.MESyncUtil;
import com.rigil.meetingapp.WMRUtil;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class MEUploadServlet extends BaseServlet {
	
	  private static final Logger logger = Logger.getLogger(MESyncServlet.class.getCanonicalName());
	  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				      throws ServletException, IOException {
	    super.doGet(req, resp);
	    logger.log(Level.INFO, "Obtaining Meeting listing - By id and sdate - start");
}
	  

	    @Override
	    public void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {

	        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
	        BlobKey blobKey = blobs.get("myFile");

	        if (blobKey == null) {
	            res.sendRedirect("/");
	        } else {
	            res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
	        }
	    }
	    
}