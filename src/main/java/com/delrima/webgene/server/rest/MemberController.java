package com.delrima.webgene.server.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.delrima.webgene.client.dao.MemberTreeDataProvider;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.model.IsTreeMember;
import com.delrima.webgene.server.services.UpdateMemberActionHandler;

@Controller
@RequestMapping(value = "/members", produces = APPLICATION_JSON_VALUE)
public class MemberController {

	final MemberTreeDataProvider dataProvider;
	UpdateMemberActionHandler updateMemberActionHandler;

	@Autowired
	public MemberController(MemberTreeDataProvider dataProvider, UpdateMemberActionHandler updateMemberActionHandler) {
		this.dataProvider = dataProvider;
		this.updateMemberActionHandler = updateMemberActionHandler;
	}

	@ResponseBody
	@RequestMapping(method = POST)
	public long addChild(@RequestBody Member member) {
		Assert.isTrue(member.getId() == null);
		return dataProvider.addMember(member).getId();
	}

	@ResponseBody
	@RequestMapping(value = "/{childId}", method = POST)
	public long addParent(@PathVariable long childId, @RequestBody Member member) {
		return updateMemberActionHandler.addParent(childId, member).getId();
	}

	@ResponseBody
	@RequestMapping(value = "/{memberId}", method = DELETE)
	public void delete(@PathVariable long memberId) {
		dataProvider.deleteMember(memberId);
	}

	@ResponseBody
	@RequestMapping(value = "/{memberId}", method = GET)
	public Member get(@PathVariable long memberId) {
		return dataProvider.retrieveMemberById(memberId);
	}

	@ResponseBody
	@RequestMapping(method = GET)
	public Set<IsTreeMember> getAll() {
		return dataProvider.retrieveAllMemberTree();
	}

	@ResponseBody
	@RequestMapping(value = "/{memberId}", method = PUT)
	public void update(@PathVariable long memberId, @RequestBody Member member) {
		Assert.isTrue(memberId == member.getId());
		updateMemberActionHandler.updateMember(member);
	}
}
