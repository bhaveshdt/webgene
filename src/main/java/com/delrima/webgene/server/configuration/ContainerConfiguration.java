package com.delrima.webgene.server.configuration;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.Aspects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import com.delrima.webgene.client.action.MemberLookupAction;
import com.delrima.webgene.client.action.RetrieveMemberTreeAction;
import com.delrima.webgene.client.action.RetrieveSingleMemberAction;
import com.delrima.webgene.client.action.SingleActionHandler;
import com.delrima.webgene.client.action.UpdateMemberAction;
import com.delrima.webgene.client.common.Action;
import com.delrima.webgene.client.common.Result;
import com.delrima.webgene.client.dao.MemberTreeDAO;
import com.delrima.webgene.client.dao.MemberTreeDataProvider;
import com.delrima.webgene.server.dao.MemberHierarchyCreatorTemplate;
import com.delrima.webgene.server.dao.MemberTreeDataProviderImpl;
import com.delrima.webgene.server.dao.orm.jpa.gae.MemberTreeDaoServerImpl;
import com.delrima.webgene.server.services.ActionHandlerServiceImpl;
import com.delrima.webgene.server.services.MemberLookupActionHandler;
import com.delrima.webgene.server.services.RetrieveMemberTreeActionHandler;
import com.delrima.webgene.server.services.RetrieveSingleMemberActionHandler;
import com.delrima.webgene.server.services.UpdateMemberActionHandler;

@Configuration
@Import(PersistenceJPAConfiguration.class)
@EnableSpringConfigured
public class ContainerConfiguration {

	@Autowired
	private PersistenceJPAConfiguration persistenceJPAConfiguration;

	@Bean
	public MemberTreeDAO familyTreeDao() {
		return new MemberTreeDaoServerImpl(persistenceJPAConfiguration.entityManager());
	}

	@Bean
	public MemberTreeDataProvider familyTreeDataProvider() {
		return new MemberTreeDataProviderImpl(familyTreeDao());
	}

	@Bean
	public MemberHierarchyCreatorTemplate MemberTreeIteratorTemplate() {
		return new MemberHierarchyCreatorTemplate(familyTreeDao());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public <R extends Result> Map<Class<Action<R>>, SingleActionHandler<Action<R>, R>> singleActionHandlerList() {
		Map<Class<Action<R>>, SingleActionHandler<Action<R>, R>> result = new HashMap<Class<Action<R>>, SingleActionHandler<Action<R>, R>>();
		result.put((Class) MemberLookupAction.class, (SingleActionHandler) new MemberLookupActionHandler(familyTreeDataProvider()));
		result.put((Class) RetrieveMemberTreeAction.class, (SingleActionHandler) new RetrieveMemberTreeActionHandler(familyTreeDataProvider()));
		result.put((Class) RetrieveSingleMemberAction.class, (SingleActionHandler) retrieveSingleMemberActionHandler());
		result.put((Class) UpdateMemberAction.class, (SingleActionHandler) new UpdateMemberActionHandler(familyTreeDataProvider(), retrieveSingleMemberActionHandler()));
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public <R extends Result> ActionHandlerServiceImpl<R> actionHandlerService() {
		return new ActionHandlerServiceImpl<R>((Map) singleActionHandlerList());
	}

	@Bean
	public RetrieveSingleMemberActionHandler retrieveSingleMemberActionHandler() {
		return new RetrieveSingleMemberActionHandler(familyTreeDataProvider());
	}

	@Bean
	public AnnotationBeanConfigurerAspect annotationBeanConfigurerAspect() {
		return Aspects.aspectOf(AnnotationBeanConfigurerAspect.class);

	}
}
