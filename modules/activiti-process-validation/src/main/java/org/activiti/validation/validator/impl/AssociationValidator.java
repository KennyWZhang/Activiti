package org.activiti.validation.validator.impl;

import java.util.Collection;
import java.util.List;

import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.activiti.validation.validator.Problems;
import org.activiti.validation.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * @author jbarrez
 */
public class AssociationValidator extends ValidatorImpl {
	
	
	@Override
	public void validate(BpmnModel bpmnModel, List<ValidationError> errors) {
		
		// Global associations
		Collection<Artifact> artifacts = bpmnModel.getGlobalArtifacts();
		if (artifacts != null) {
			for (Artifact artifact : artifacts) {
				if (artifact instanceof Association) {
					validate(null, (Association) artifact, errors);
				}
			}
		}
		
		// Process associations
		for (Process process : bpmnModel.getProcesses()) {
			artifacts = process.getArtifacts();
			for (Artifact artifact : artifacts) {
				if (artifact instanceof Association) {
					validate(process, (Association) artifact, errors);
				}
			}
		}
		
	}

	protected void validate(Process process, Association association, List<ValidationError> errors) {
	 if(StringUtils.isEmpty(association.getSourceRef())) {
		 addError(errors, Problems.ASSOCIATION_INVALID_SOURCE_REFERENCE, process, association, 
				 "association element missing attribute 'sourceRef'");
    }
    if(StringUtils.isEmpty(association.getTargetRef())) {
    	addError(errors, Problems.ASSOCIATION_INVALID_TARGET_REFERENCE, process, association, 
    			"association element missing attribute 'targetRef'");
    }
  }

}