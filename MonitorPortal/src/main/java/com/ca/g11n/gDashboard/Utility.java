package com.ca.g11n.gDashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {

// package-private methods

	static List<Project> convertProjectAndLanguageListToProjectList( List<ProjectAndLanguage> projectAndLanguageList ) {
		List<Project> projectList = new ArrayList<Project>();
		
		Map<String, Project> projectIdProjectMap = new HashMap<String, Project>();
		Map<String, List<Language>> projectIdLanguageListMap = new HashMap<String, List<Language>>();
		for( ProjectAndLanguage projectAndLanguage : projectAndLanguageList ) {
			String projectId = projectAndLanguage.getProjectId();
			
			// project
			Project project = projectIdProjectMap.get( projectId );
			if( project ==  null ) {
				project = new Project( projectId, projectAndLanguage.getProjectName(), projectAndLanguage.getPmName(), null );
				projectIdProjectMap.put( projectId, project );
			}
			
			// language list
			List<Language> languageList = projectIdLanguageListMap.get( projectId );
			if( languageList == null ) {
				languageList = new ArrayList<Language>();
				projectIdLanguageListMap.put( projectId, languageList );
			}
			Language language = Language.getLanguageFromLanguageCode( projectAndLanguage.getLanguageCode() );
			assert language == null : "Unknown language code: " + projectAndLanguage.getLanguageCode();
			languageList.add( language );
		}
		
		for( String projectId : projectIdProjectMap.keySet() ) {
			Project project = projectIdProjectMap.get( projectId );
			List<Language> languageList = projectIdLanguageListMap.get( projectId );
			project.setLanguageList( languageList );
			projectList.add( project );
		}
		
		return projectList;
	}

}
