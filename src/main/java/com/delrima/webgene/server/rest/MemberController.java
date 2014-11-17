package com.delrima.webgene.server.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.delrima.webgene.client.dao.MemberTreeDataProvider;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.model.IsTreeMember;

@Controller
@RequestMapping(value = "/member", produces = APPLICATION_JSON_VALUE)
public class MemberController {

	MemberTreeDataProvider dataProvider;

	@Autowired
	public MemberController(MemberTreeDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	@ResponseBody
	@RequestMapping(method = POST)
	public long add(@RequestBody Member member) {
		return dataProvider.addMember(member).getId();
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
		dataProvider.updateMember(member);
	}
}
