package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.update;

import java.io.File;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.Init;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.OSUtil;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.FileUtil;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.LauncherFile;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.Logger;

public class GameParser {
	
	public static GameUpdater updater = new GameUpdater();
	
	  public static void prepareFiles() {
		Logger.write("Reading XML from " + Init.getConfiguration().getDownloadUrl() + "downloads.xml");
	      try {
	          final URL resourceUrl = new URL(Init.getConfiguration().getDownloadUrl() + "downloads.xml");
	          final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	          final DocumentBuilder db = dbf.newDocumentBuilder();
	          final Document doc = db.parse(resourceUrl.openConnection().getInputStream());
	          doc.getDocumentElement().normalize();
	          final NodeList nodeLst = doc.getElementsByTagName("Contents");
	          
	          final long start = System.nanoTime();
	          for(int i = 0; i < nodeLst.getLength(); i++) {
	              final Node node = nodeLst.item(i);
	              if(node.getNodeType() == Node.ELEMENT_NODE) {
	                  final Element element = (Element) node;
	                  final String key = element.getElementsByTagName("Key").item(0).getChildNodes().item(0).getNodeValue();
	                  String etag = element.getElementsByTagName("ETag") != null ? element.getElementsByTagName("ETag").item(0).getChildNodes().item(0).getNodeValue() : "-";
	                  final long size = Long.parseLong(element.getElementsByTagName("Size").item(0).getChildNodes().item(0).getNodeValue());
	                  
					 File localFile = new File(OSUtil.getDirectory(), key);
					 GameDownloader.addToVerifyIntegrity(key);
						if (!localFile.isDirectory()) {				
	                      if(etag.length() > 1) {
	                          etag = FileUtil.getEtag(etag);
	                          if (localFile.exists()) {
	                          	 if(localFile.isFile() && localFile.length() == size) {
	                                 final String localMd5 = FileUtil.getMD5(localFile);
	                                 if(!localMd5.equals(etag)) {
	                         			updater.downloadTask.needToDownload++;
			                          	if (!(Init.getConfiguration().getDownloadUrl() + key).endsWith("/")) {
			                    			if (key.contains("jre-64") || key.contains("jre-32")) {
			                    				if (System.getProperty("os.arch").contains("64")) {
			                    					if (key.contains("jre-64")) {
			        	                    			updater.filesToUpdate.put(key, new LauncherFile(size, Init.getConfiguration().getDownloadUrl() + key, localFile.getAbsolutePath()));
			                    					}
			                    				}
			                    				else {
			                    					if (key.contains("jre-32")) {
			        	                    			updater.filesToUpdate.put(key, new LauncherFile(size, Init.getConfiguration().getDownloadUrl() + key, localFile.getAbsolutePath()));
			                    					}
			                    				}
			                    			}
			                    			else {
				                    			updater.filesToUpdate.put(key, new LauncherFile(size, Init.getConfiguration().getDownloadUrl() + key, localFile.getAbsolutePath()));
			                    			}
			                          	}
	                                 }
	                          	 }
		                          else {
			                          	if (!(Init.getConfiguration().getDownloadUrl() + key).endsWith("/")) {
			                    			updater.downloadTask.needToDownload++;
			                    			if (key.contains("jre-64") || key.contains("jre-32")) {
			                    				if (System.getProperty("os.arch").contains("64")) {
			                    					if (key.contains("jre-64")) {
			        	                    			updater.filesToUpdate.put(key, new LauncherFile(size, Init.getConfiguration().getDownloadUrl() + key, localFile.getAbsolutePath()));
			                    					}
			                    				}
			                    				else {
			                    					if (key.contains("jre-32")) {
			        	                    			updater.filesToUpdate.put(key, new LauncherFile(size, Init.getConfiguration().getDownloadUrl() + key, localFile.getAbsolutePath()));
			                    					}
			                    				}
			                    			}
			                    			else {
				                    			updater.filesToUpdate.put(key, new LauncherFile(size, Init.getConfiguration().getDownloadUrl() + key, localFile.getAbsolutePath()));
			                    			}
			                          	}
		                          }
	                          }
	                          else {
	                          	if (!(Init.getConfiguration().getDownloadUrl() + key).endsWith("/")) {
	                    			updater.downloadTask.needToDownload++;
	                    			if (key.contains("jre-64") || key.contains("jre-32")) {
	                    				if (System.getProperty("os.arch").contains("64")) {
	                    					if (key.contains("jre-64")) {
	        	                    			updater.filesToUpdate.put(key, new LauncherFile(size, Init.getConfiguration().getDownloadUrl() + key, localFile.getAbsolutePath()));
	                    					}
	                    				}
	                    				else {
	                    					if (key.contains("jre-32")) {
	        	                    			updater.filesToUpdate.put(key, new LauncherFile(size, Init.getConfiguration().getDownloadUrl() + key, localFile.getAbsolutePath()));
	                    					}
	                    				}
	                    			}
	                    			else {
		                    			updater.filesToUpdate.put(key, new LauncherFile(size, Init.getConfiguration().getDownloadUrl() + key, localFile.getAbsolutePath()));
	                    			}
	                          	}
	                          }
	                          
	                      }
						}
						else {
							localFile.mkdir();
							localFile.mkdirs();
						}
	              }
	          }
	          final long end = System.nanoTime();
	          final long delta = end - start;
	          Logger.write("Temps (delta) pour comparer les ressources: " + delta / 1000000L + " ms");
	      }
	      catch(final Exception ex) {
//	    	  new LauncherAlert("Impossible de verifier l'installation", "Veuillez nous excuser mais nous n'avons pas pu effectuer la verification des ressources depuis l'hote distant.\nNous allons amorcer un telechargement de l'integralite des ressources afin de vous proposer une experience de jeu optimale.");
	    	  Logger.write("Impossible de telecharger les ressources (" + ex + ")");
	      }
	  }
}
