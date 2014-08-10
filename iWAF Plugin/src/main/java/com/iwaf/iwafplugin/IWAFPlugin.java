package com.iwaf.iwafplugin;
import hudson.Launcher;
import hudson.Extension;
import hudson.util.FormValidation;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.AbstractProject;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;

public class IWAFPlugin extends Builder {

	private final String properties;

	@DataBoundConstructor
	public IWAFPlugin(String properties) {
		this.properties = properties;
	}
	public String getProperties() {
		return properties;
	}

	@Override
	public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {
		listener.getLogger().println("Properties:" +properties);
		return true;
	}

	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl)super.getDescriptor();
	}

	@Extension 
	public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

		private boolean svnPath;

		public DescriptorImpl() {
			load();
		}
		public FormValidation doCheckProperties(@QueryParameter String properties)
				throws IOException, ServletException {
			if (properties.length() == 0)
				return FormValidation.error("Please set the properties");
			if (properties.length() < 4)
				return FormValidation.warning("Too many properties");
			return FormValidation.ok();
		}

		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}
		public String getDisplayName() {
			return "Add Properties";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
			svnPath = formData.getBoolean("svnPath");
			save();
			return super.configure(req,formData);
		}

		public boolean getSVNPath() {
			return svnPath;
		}
	}
}

