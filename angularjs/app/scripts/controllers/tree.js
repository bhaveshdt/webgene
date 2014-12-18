'use strict';

function Tree(context) {
	var argLabels = [ 'el', 'plevel', 'slevel', 'isleaf', 'firstChildLeft', 'lastChildLeft', 'topVal', 'leftVal' ];
	function log(args) {
		var i;
		var obj = {};
		for (i = 0; i < argLabels.length; i++) {
			obj[argLabels[i]] = args[i];
		}
		console.log(JSON.stringify(obj));
	}
	this.create = function(memberId, containerId, data, orientation) {
		context.orientation = orientation;
		context.data = data;
		context.id = containerId;
		function getMember(id) {
			return (_.filter(context.data, function(val) {
				return val && val[0] == id;
			}))[0];
		}
		function getChildren(id) {
			return _.filter(context.data, function(val) {
				return val[1] == id || val[2] == id;
			});
		}
		function getParents(id1, id2) {
			return _.filter([ getMember(id1), getMember(id2) ], function(val) {
				return val;
			});
		}
		var descendantTreeCreator = new TreeCreator({
			id : 'descendants',
			elevator : 1,
			getChildNodes : function(m) {
				return getChildren(m[0])
			}
		}, context);
		var ancestorTreeCreator = new TreeCreator({
			id : 'ancestors',
			elevator : -1,
			getChildNodes : function(m) {
				return getParents(m[1], m[2])
			}
		}, context);
		var descendantRootNodeInfo = descendantTreeCreator.createNodeTree(getMember(memberId), 0, 0);
		var ancestorRootNodeInfo = ancestorTreeCreator.createNodeTree(getMember(memberId), 0, 0, descendantRootNodeInfo.el);
		$(descendantRootNodeInfo.el).addClass('root');
		if (ancestorTreeCreator.isVerticalOrientation()) {
			var difference = descendantRootNodeInfo.left - ancestorRootNodeInfo.left;
			ancestorTreeCreator.getContainerEl().css('top', ancestorTreeCreator.getHeight() + 'px');
			ancestorTreeCreator.getContainerEl().css('height', ancestorTreeCreator.getHeight() + 'px');
			descendantRootNodeInfo.el.style.left = descendantRootNodeInfo.left + 'px';
			((difference > 0 ? ancestorTreeCreator : descendantTreeCreator).getContainerEl()).css('left', Math.abs(difference) + 'px');
		} else {
			var difference = descendantRootNodeInfo.top - ancestorRootNodeInfo.top;
			ancestorTreeCreator.getContainerEl().css('left', ancestorTreeCreator.getWidth() + 'px');
			descendantTreeCreator.getContainerEl().css('left', ancestorTreeCreator.getWidth() + 'px');
			descendantRootNodeInfo.el.style.top = descendantRootNodeInfo.top + 'px';
			((difference > 0 ? ancestorTreeCreator : descendantTreeCreator).getContainerEl()).css('top', Math.abs(difference) + 'px');
			if (difference <= 0) {
				((descendantTreeCreator).getContainerEl()).css('left', Math.abs(ancestorTreeCreator.getWidth()) + 'px');
			}
		}
	}
	function TreeCreator(context, treeContext) {
		this.isVerticalOrientation = function() {
			return (treeContext.orientation == 'vertical');
		};
		var height = 0;
		var width = 0;
		var containerEl = $('#' + treeContext.id + '_' + context.id);
		this.getContainerEl = function() {
			return containerEl
		};
		var evaluatePosition = this.isVerticalOrientation() ? evaluatePositionForVerticalTree : evaluatePositionForHorizontalTree;
		function evaluatePositionForHorizontalTree(el, plevel, slevel, isleaf, firstChild, lastChild) {
			var fromtop = slevel * (treeContext.elHeight + treeContext.margin);
			var center = (lastChild.top - firstChild.top) / 2;
			var topVal = (center + firstChild.top) + (isleaf ? fromtop : 0);
			var leftVal = (plevel * (treeContext.elWidth + treeContext.margin));
			log([ $(el).html(), plevel, slevel, isleaf, firstChild.left, lastChild.left, topVal, leftVal ]);
			return {
				top : topVal,
				left : leftVal
			};
		}
		function evaluatePositionForVerticalTree(el, plevel, slevel, isleaf, firstChild, lastChild) {
			var indent = slevel * (treeContext.elWidth + treeContext.margin);
			var center = (lastChild.left - firstChild.left) / 2;
			var topVal = (plevel * (treeContext.elHeight + treeContext.margin));
			var leftVal = (center + firstChild.left) + (isleaf ? indent : 0);
			log([ $(el).html(), plevel, slevel, isleaf, firstChild.left, lastChild.left, topVal, leftVal ]);
			return {
				top : topVal,
				left : leftVal
			};
		}
		function createNode(m) {
			var el = treeContext.createNodeElement(m);
			containerEl.append(el);
			$(el).attr('id', 'family' + m[0]);
			$(el).addClass('member');
			return el;
		}
		function doCreateNodeTreeWithEl(m, plevel, slevel, el) {
			var children = context.getChildNodes(m);
			var span = 0, firstChild = {
				left : 0,
				top : 0
			}, lastChild = {
				left : 0,
				top : 0
			};
			_.each(children, function(m, idx) {
				span += (lastChild = doCreateNodeTree(m, (plevel + context.elevator), span + slevel)).span;
				if (idx == 0) {
					firstChild = lastChild
				}
			});
			var isleaf = (!children || children.length == 0);
			span = isleaf ? 1 : span;
			var position = evaluatePosition(el, plevel, slevel, isleaf, firstChild, lastChild); 
			el.style.top = position.top + 'px';
			el.style.left = position.left + 'px';
			height = Math.max(height, Math.abs(position.top));
			width = Math.max(width, Math.abs(position.left));
			return {
				el : el,
				span : span,
				left : position.left,
				top : position.top
			};
		}
		function doCreateNodeTree(m, plevel, slevel) {
			if (!m) {
				return 0;
			}
			return doCreateNodeTreeWithEl(m, plevel, slevel, createNode(m));
		}
		function reset(el, rootel) {
			el.removeClass();
			el.html('');
			el.attr('style', '');
			el.addClass(context.id + ' tree ' + treeContext.orientation + '-tree');
			if (rootel) {
				$(rootel).addClass('root')
			}
		}
		this.getHeight = function() {
			return height;
		};
		this.getWidth = function() {
			return width;
		};
		this.createNodeTree = function(m, plevel, slevel, el) {
			reset($(containerEl), el);
			return el ? doCreateNodeTreeWithEl(m, plevel, slevel, el) : doCreateNodeTree(m, plevel, slevel)
		};
		return this;
	}
}