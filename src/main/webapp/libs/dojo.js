/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http:
*/

/*
	This is an optimized version of Dojo, built for deployment and not for
	development. To get sources and documentation, please visit:

		http:
*/

(function(
	userConfig,
	defaultConfig
){
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	// This function defines an AMD-compliant (http:
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	var	noop = function(){
		},

		isEmpty = function(it){
			for(var p in it){
				return 0;
			}
			return 1;
		},

		toString = {}.toString,

		isFunction = function(it){
			return toString.call(it) == "[object Function]";
		},

		isString = function(it){
			return toString.call(it) == "[object String]";
		},

		isArray = function(it){
			return toString.call(it) == "[object Array]";
		},

		forEach = function(vector, callback){
			if(vector){
				for(var i = 0; i < vector.length;){
					callback(vector[i++]);
				}
			}
		},

		mix = function(dest, src){
			for(var p in src){
				dest[p] = src[p];
			}
			return dest;
		},

		makeError = function(error, info){
			return mix(new Error(error), {src:"dojoLoader", info:info});
		},

		uidSeed = 1,

		uid = function(){
			
			return "_" + uidSeed++;
		},

		

		
		req = function(
			config,       
			dependencies, 
			callback      
		){
			return contextRequire(config, dependencies, callback, 0, req);
		},

		
		global = this,

		doc = global.document,

		element = doc && doc.createElement("DiV"),

		has = req.has = function(name){
			return hasCache[name] = isFunction(hasCache[name]) ? hasCache[name](global, doc, element) : hasCache[name];
		},

		hasCache = has.cache = defaultConfig.hasCache;

	has.add = function(name, test, now, force){
		(hasCache[name]===undefined || force) && (hasCache[name] = test);
		return now && has(name);
	};

	false && has.add("host-node", typeof process == "object" && /node(\.exe)?$/.test(process.execPath));
	if(0){
		
		require("./_base/configNode.js").config(defaultConfig);
		
		defaultConfig.loaderPatch.nodeRequire = require;
	}

	false && has.add("host-rhino", typeof load == "function" && (typeof Packages == "function" || typeof Packages == "object"));
	if(0){
		
		for(var baseUrl = userConfig.baseUrl || ".", arg, rhinoArgs = this.arguments, i = 0; i < rhinoArgs.length;){
			arg = (rhinoArgs[i++] + "").split("=");
			if(arg[0] == "baseUrl"){
				baseUrl = arg[1];
				break;
			}
		}
		load(baseUrl + "/_base/configRhino.js");
		rhinoDojoConfig(defaultConfig, baseUrl, rhinoArgs);
	}

	
	
	for(var p in userConfig.has){
		has.add(p, userConfig.has[p], 0, 1);
	}

	
	
	

	
	
	var	requested = 1,
		arrived = 2,
		nonmodule = 3,
		executing = 4,
		executed = 5;

	if(0){
		
		requested = "requested";
		arrived = "arrived";
		nonmodule = "not-a-module";
		executing = "executing";
		executed = "executed";
	}

	var legacyMode = 0,
		sync = "sync",
		xd = "xd",
		syncExecStack = [],
		dojoRequirePlugin = 0,
		checkDojoRequirePlugin = noop,
		transformToAmd = noop,
		getXhr;
	if(1){
		req.isXdUrl = noop;

		req.initSyncLoader = function(dojoRequirePlugin_, checkDojoRequirePlugin_, transformToAmd_){
			if(!dojoRequirePlugin){
				dojoRequirePlugin = dojoRequirePlugin_;
				checkDojoRequirePlugin = checkDojoRequirePlugin_;
				transformToAmd = transformToAmd_;
			}
			return {
				sync:sync,
				xd:xd,
				arrived:arrived,
				nonmodule:nonmodule,
				executing:executing,
				executed:executed,
				syncExecStack:syncExecStack,
				modules:modules,
				execQ:execQ,
				getModule:getModule,
				injectModule:injectModule,
				setArrived:setArrived,
				signal:signal,
				finishExec:finishExec,
				execModule:execModule,
				dojoRequirePlugin:dojoRequirePlugin,
				getLegacyMode:function(){return legacyMode;},
				holdIdle:function(){checkCompleteGuard++;},
				releaseIdle:function(){checkIdle();}
			};
		};

		if(1){
			
			
			
			

			var locationProtocol = location.protocol,
				locationHost = location.host,
				fileProtocol = !locationHost;
			req.isXdUrl = function(url){
				if(fileProtocol || /^\./.test(url)){
					
					return false;
				}
				if(/^\/\
					// for v1.6- backcompat, url starting with 
					return true;
				}
				
				var match = url.match(/^([^\/\:]+\:)\/\/([^\/]+)/);
				return match && (match[1] != locationProtocol || match[2] != locationHost);
			};

			// note: to get the file:
			true || has.add("dojo-xhr-factory", 1);
			has.add("dojo-force-activex-xhr", 1 && !doc.addEventListener && window.location.protocol == "file:");
			has.add("native-xhr", typeof XMLHttpRequest != "undefined");
			if(has("native-xhr") && !has("dojo-force-activex-xhr")){
				getXhr = function(){
					return new XMLHttpRequest();
				};
			}else{
				
				for(var XMLHTTP_PROGIDS = ['Msxml2.XMLHTTP', 'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.4.0'], progid, i = 0; i < 3;){
					try{
						progid = XMLHTTP_PROGIDS[i++];
						if(new ActiveXObject(progid)){
							
							break;
						}
					}catch(e){
						
						
						
					}
				}
				getXhr = function(){
					return new ActiveXObject(progid);
				};
			}
			req.getXhr = getXhr;

			has.add("dojo-gettext-api", 1);
			req.getText = function(url, async, onLoad){
				var xhr = getXhr();
				xhr.open('GET', fixupUrl(url), false);
				xhr.send(null);
				if(xhr.status == 200 || (!location.host && !xhr.status)){
					if(onLoad){
						onLoad(xhr.responseText, async);
					}
				}else{
					throw makeError("xhrFailed", xhr.status);
				}
				return xhr.responseText;
			};
		}
	}else{
		req.async = 1;
	}

	
	
	
	var eval_ =
		
		new Function("__text", 'return eval(__text);');

	req.eval =
		function(text, hint){
			return eval_(text + "\r\n//
		};

	
	
	
	var listenerQueues = {},
		error = "error",
		signal = req.signal = function(type, args){
			var queue = listenerQueues[type];
			
			
			forEach(queue && queue.slice(0), function(listener){
				listener.apply(null, isArray(args) ? args : [args]);
			});
		},
		on = req.on = function(type, listener){
			
			var queue = listenerQueues[type] || (listenerQueues[type] = []);
			queue.push(listener);
			return {
				remove:function(){
					for(var i = 0; i<queue.length; i++){
						if(queue[i]===listener){
							queue.splice(i, 1);
							return;
						}
					}
				}
			};
		};

	
	
	
	var
		aliases
			
			= [],

		paths
			
			= {},

		pathsMapProg
			
			
			= [],

		packs
			
			= {},

		packageMap
			
			= {},

		packageMapProg
			
			
			= [],

		modules
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			= {},

		cacheBust
			
			= "",

		cache
			
			
			
			
			 = {},

		pendingCacheInsert
			
			
			
			
			
			
			 = {},

		dojoSniffConfig
			
			
			= {};

	if(1){
		var consumePendingCacheInsert = function(referenceModule){
				for(var p in pendingCacheInsert){
					var match = p.match(/^url\:(.+)/);
					if(match){
						cache[toUrl(match[1], referenceModule)] =  pendingCacheInsert[p];
					}else if(p!="*noref"){
						cache[getModuleInfo(p, referenceModule).mid] = pendingCacheInsert[p];
					}
				}
				pendingCacheInsert = {};
			},

			computeMapProg = function(map, dest, packName){
				
				
				
				
				
				
				
				dest.splice(0, dest.length);
				var p, i, item, reverseName = 0;
				for(p in map){
					dest.push([p, map[p]]);
					if(map[p]==packName){
						reverseName = p;
					}
				}
				dest.sort(function(lhs, rhs){
					return rhs[0].length - lhs[0].length;
				});
				for(i = 0; i < dest.length;){
					item = dest[i++];
					item[2] = new RegExp("^" + item[0].replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, function(c){ return "\\" + c; }) + "(\/|$)");
					item[3] = item[0].length + 1;
				}
				return reverseName;
			},

			fixupPackageInfo = function(packageInfo, baseUrl){
				
				var name = packageInfo.name;
				if(!name){
					
					name = packageInfo;
					packageInfo = {name:name};
				}
				packageInfo = mix({main:"main", mapProg:[]}, packageInfo);
				packageInfo.location = (baseUrl || "") + (packageInfo.location ? packageInfo.location : name);
				packageInfo.reverseName = computeMapProg(packageInfo.packageMap, packageInfo.mapProg, name);

				if(!packageInfo.main.indexOf("./")){
					packageInfo.main = packageInfo.main.substring(2);
				}

				
				
				mix(paths, packageInfo.paths);

				
				packs[name] = packageInfo;
				packageMap[name] = name;
			},

			config = function(config, booting){
				for(var p in config){
					if(p=="waitSeconds"){
						req.waitms = (config[p] || 0) * 1000;
					}
					if(p=="cacheBust"){
						cacheBust = config[p] ? (isString(config[p]) ? config[p] : (new Date()).getTime() + "") : "";
					}
					if(p=="baseUrl" || p=="combo"){
						req[p] = config[p];
					}
					if(1 && p=="async"){
						
						
						
						
						
						var mode = config[p];
						req.legacyMode = legacyMode = (isString(mode) && /sync|legacyAsync/.test(mode) ? mode : (!mode ? "sync" : false));
						req.async = !legacyMode;
					}
					if(config[p]!==hasCache){
						
						req.rawConfig[p] = config[p];
						p!="has" && has.add("config-"+p, config[p], 0, booting);
					}
				}

				
				if(!req.baseUrl){
					req.baseUrl = "./";
				}
				
				if(!/\/$/.test(req.baseUrl)){
					req.baseUrl += "/";
				}

				

				for(p in config.has){
					has.add(p, config.has[p], 0, booting);
				}

				
				forEach(config.packages, fixupPackageInfo);

				
				for(baseUrl in config.packagePaths){
					forEach(config.packagePaths[baseUrl], function(packageInfo){
						fixupPackageInfo(packageInfo, baseUrl + "/");
					});
				}

				
				
				computeMapProg(mix(paths, config.paths), pathsMapProg);

				
				forEach(config.aliases, function(pair){
					if(isString(pair[0])){
						pair[0] = new RegExp("^" + pair[0].replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, function(c){return "\\" + c;}) + "$");
					}
					aliases.push(pair);
				});

				
				computeMapProg(mix(packageMap, config.packageMap), packageMapProg);

				
				if(config.cache){
					consumePendingCacheInsert();
					pendingCacheInsert = config.cache;
					if(config.cache["*noref"]){
						consumePendingCacheInsert();
					}
				}

				signal("config", [config, req.rawConfig]);
			};

		
		
		

		if(1){
			for(var src, match, scripts = doc.getElementsByTagName("script"), i = 0; i < scripts.length && !match; i++){
				if((src = scripts[i].getAttribute("src")) && (match = src.match(/(.*)\/?dojo\.js(\W|$)/i))){
					
					userConfig.baseUrl = userConfig.baseUrl || defaultConfig.baseUrl || match[1];

					
					src = (scripts[i].getAttribute("data-dojo-config") || scripts[i].getAttribute("djConfig"));
					if(src){
						dojoSniffConfig = req.eval("({ " + src + " })", "data-dojo-config");
					}
					if(0){
						var dataMain = scripts[i].getAttribute("data-main");
						if(dataMain){
							dojoSniffConfig.deps = dojoSniffConfig.deps || [dataMain];
						}
					}
				}
			}
		}

		if(0){
			
			try{
				if(window.parent != window && window.parent.require){
					var doh = window.parent.require("doh");
					doh && mix(dojoSniffConfig, doh.testConfig);
				}
			}catch(e){}
		}

		
		req.rawConfig = {};
		config(defaultConfig, 1);
		config(userConfig, 1);
		config(dojoSniffConfig, 1);
	}else{
		
		paths = defaultConfig.paths;
		pathsMapProg = defaultConfig.pathsMapProg;
		packs = defaultConfig.packs;
		aliases = defaultConfig.aliases;
		packageMap = defaultConfig.packageMap;
		packageMapProg = defaultConfig.packageMapProg;
		modules = defaultConfig.modules;
		cache = defaultConfig.cache;
		cacheBust = defaultConfig.cacheBust;

		
		req.rawConfig = defaultConfig;
	}


	if(0){
		req.combo = req.combo || {add:noop};
		var	comboPending = 0,
			combosPending = [],
			comboPendingTimer = null;
	}


	
	var	injectDependencies = function(module){
			
			checkCompleteGuard++;
			forEach(module.deps, injectModule);
			if(0 && comboPending && !comboPendingTimer){
				comboPendingTimer = setTimeout(function() {
					comboPending = 0;
					comboPendingTimer = null;
					req.combo.done(function(mids, url) {
						var onLoadCallback= function(){
							
							runDefQ(0, mids);
							checkComplete();
						};
						combosPending.push(mids);
						injectingModule = mids;
						req.injectUrl(url, onLoadCallback, mids);
						injectingModule = 0;
					}, req);
				}, 0);
			}
			checkIdle();
		},

		contextRequire = function(a1, a2, a3, referenceModule, contextRequire){
			var module, syntheticMid;
			if(isString(a1)){
				
				module = getModule(a1, referenceModule, true);
				if(module && module.executed){
					return module.result;
				}
				throw makeError("undefinedModule", a1);
			}
			if(!isArray(a1)){
				
				config(a1);

				
				a1 = a2;
				a2 = a3;
			}
			if(isArray(a1)){
				

				syntheticMid = "require*" + uid();

				
				for(var mid, deps = [], i = 0; i < a1.length;){
					mid = a1[i++];
					if(mid in {exports:1, module:1}){
						throw makeError("illegalModuleId", mid);
					}
					deps.push(getModule(mid, referenceModule));
				}

				
				module = mix(makeModuleInfo("", syntheticMid, 0, ""), {
					injected: arrived,
					deps: deps,
					def: a2 || noop,
					require: referenceModule ? referenceModule.require : req
				});
				modules[module.mid] = module;

				
				injectDependencies(module);

				
				
				
				
				var strict = checkCompleteGuard && req.async;
				checkCompleteGuard++;
				execModule(module, strict);
				checkIdle();
				if(!module.executed){
					
					execQ.push(module);
				}
				checkComplete();
			}
			return contextRequire;
		},

		createRequire = function(module){
			if(!module){
				return req;
			}
			var result = module.require;
			if(!result){
				result = function(a1, a2, a3){
					return contextRequire(a1, a2, a3, module, result);
				};
				module.require = mix(result, req);
				result.module = module;
				result.toUrl = function(name){
					return toUrl(name, module);
				};
				result.toAbsMid = function(mid){
					return toAbsMid(mid, module);
				};
				if(0){
					result.undef = function(mid){
						req.undef(mid, module);
					};
				}
			}
			return result;
		},

		execQ =
			
			[],

		defQ =
			
			[],

		waiting =
			
			{},

		setRequested = function(module){
			module.injected = requested;
			waiting[module.mid] = 1;
			if(module.url){
				waiting[module.url] = module.pack || 1;
			}
		},

		setArrived = function(module){
			module.injected = arrived;
			delete waiting[module.mid];
			if(module.url){
				delete waiting[module.url];
			}
			if(isEmpty(waiting)){
				clearTimer();
				1 && legacyMode==xd && (legacyMode = sync);
			}
		},

		execComplete = req.idle =
			
			function(){
				return !defQ.length && isEmpty(waiting) && !execQ.length && !checkCompleteGuard;
			},

		runMapProg = function(targetMid, map){
			
			for(var i = 0; i < map.length; i++){
				if(map[i][2].test(targetMid)){
					return map[i];
				}
			}
			return 0;
		},

		compactPath = function(path){
			var result = [],
				segment, lastSegment;
			path = path.replace(/\\/g, '/').split('/');
			while(path.length){
				segment = path.shift();
				if(segment==".." && result.length && lastSegment!=".."){
					result.pop();
					lastSegment = result[result.length - 1];
				}else if(segment!="."){
					result.push(lastSegment= segment);
				} 
			}
			return result.join("/");
		},

		makeModuleInfo = function(pid, mid, pack, url, cacheId){
			if(1){
				var xd= req.isXdUrl(url);
				return {pid:pid, mid:mid, pack:pack, url:url, executed:0, def:0, isXd:xd, isAmd:!!(xd || (packs[pid] && packs[pid].isAmd)), cacheId:cacheId};
			}else{
				return {pid:pid, mid:mid, pack:pack, url:url, executed:0, def:0, cacheId:cacheId};
			}
		},

		getModuleInfo_ = function(mid, referenceModule, packs, modules, baseUrl, packageMapProg, pathsMapProg, alwaysCreate){
			
			
			var pid, pack, midInPackage, mapProg, mapItem, path, url, result, isRelative, requestedMid, cacheId=0;
			requestedMid = mid;
			isRelative = /^\./.test(mid);
			if(/(^\/)|(\:)|(\.js$)/.test(mid) || (isRelative && !referenceModule)){
				
				
				return makeModuleInfo(0, mid, 0, mid);
			}else{
				
				mid = compactPath(isRelative ? (referenceModule.mid + "/../" + mid) : mid);
				if(/^\./.test(mid)){
					throw makeError("irrationalPath", mid);
				}
				
				mapProg = referenceModule && referenceModule.pack && referenceModule.pack.mapProg;
				mapItem = (mapProg && runMapProg(mid, mapProg)) || runMapProg(mid, packageMapProg);
				if(mapItem){
					
					
					pid = mapItem[1];
					mid = mid.substring(mapItem[3]);
					pack = packs[pid];
					if(!mid){
						mid= pack.main;
					}
					midInPackage = mid;
					cacheId = pack.reverseName + "/" + mid;
					mid = pid + "/" + mid;
				}else{
					pid = "";
				}

				
				var candidateLength = 0,
					candidate = 0;
				forEach(aliases, function(pair){
					var match = mid.match(pair[0]);
					if(match && match.length>candidateLength){
						candidate = isFunction(pair[1]) ? mid.replace(pair[0], pair[1]) : pair[1];
					}
				});
				if(candidate){
					return getModuleInfo_(candidate, 0, packs, modules, baseUrl, packageMapProg, pathsMapProg, alwaysCreate);
				}

				result = modules[mid];
				if(result){
					return alwaysCreate ? makeModuleInfo(result.pid, result.mid, result.pack, result.url, cacheId) : modules[mid];
				}
			}
			
			

			mapItem = runMapProg(mid, pathsMapProg);
			if(mapItem){
				url = mapItem[1] + mid.substring(mapItem[3] - 1);
			}else if(pid){
				url = pack.location + "/" + midInPackage;
			}else if(has("config-tlmSiblingOfDojo")){
				url = "../" + mid;
			}else{
				url = mid;
			}
			
			if(!(/(^\/)|(\:)/.test(url))){
				url = baseUrl + url;
			}
			url += ".js";
			return makeModuleInfo(pid, mid, pack, compactPath(url), cacheId);
		},

		getModuleInfo = function(mid, referenceModule){
			return getModuleInfo_(mid, referenceModule, packs, modules, req.baseUrl, packageMapProg, pathsMapProg);
		},

		resolvePluginResourceId = function(plugin, prid, referenceModule){
			return plugin.normalize ? plugin.normalize(prid, function(mid){return toAbsMid(mid, referenceModule);}) : toAbsMid(prid, referenceModule);
		},

		dynamicPluginUidGenerator = 0,

		getModule = function(mid, referenceModule, immediate){
			
			var match, plugin, prid, result;
			match = mid.match(/^(.+?)\!(.*)$/);
			if(match){
				
				plugin = getModule(match[1], referenceModule, immediate);

				if(1 && legacyMode == sync && !plugin.executed){
					injectModule(plugin);
					if(plugin.injected===arrived && !plugin.executed){
						checkCompleteGuard++;
						execModule(plugin);
						checkIdle();
					}
					if(plugin.executed){
						promoteModuleToPlugin(plugin);
					}else{
						
						execQ.unshift(plugin);
					}
				}



				if(plugin.executed === executed && !plugin.load){
					
					promoteModuleToPlugin(plugin);
				}

				
				if(plugin.load){
					prid = resolvePluginResourceId(plugin, match[2], referenceModule);
					mid = (plugin.mid + "!" + (plugin.dynamic ? ++dynamicPluginUidGenerator + "!" : "") + prid);
				}else{
					prid = match[2];
					mid = plugin.mid + "!" + (++dynamicPluginUidGenerator) + "!waitingForPlugin";
				}
				result = {plugin:plugin, mid:mid, req:createRequire(referenceModule), prid:prid};
			}else{
				result = getModuleInfo(mid, referenceModule);
			}
			return modules[result.mid] || (!immediate && (modules[result.mid] = result));
		},

		toAbsMid = req.toAbsMid = function(mid, referenceModule){
			return getModuleInfo(mid, referenceModule).mid;
		},

		toUrl = req.toUrl = function(name, referenceModule){
			
			var	match = name.match(/(.+)(\.[^\/\.]+?)$/),
				root = (match && match[1]) || name,
				ext = (match && match[2]) || "",
				moduleInfo = getModuleInfo(root, referenceModule),
				url= moduleInfo.url;
			
			url= typeof moduleInfo.pid == "string" ? url.substring(0, url.length - 3) : url;
			return fixupUrl(url + ext);
		},

		nonModuleProps = {
			injected: arrived,
			executed: executed,
			def: nonmodule,
			result: nonmodule
		},

		makeCjs = function(mid){
			return modules[mid] = mix({mid:mid}, nonModuleProps);
		},

		cjsRequireModule = makeCjs("require"),
		cjsExportsModule = makeCjs("exports"),
		cjsModuleModule = makeCjs("module"),

		runFactory = function(module, args){
			req.trace("loader-run-factory", [module.mid]);
			var factory = module.def,
				result;
			1 && syncExecStack.unshift(module);
			if(has("config-dojo-loader-catches")){
				try{
					result= isFunction(factory) ? factory.apply(null, args) : factory;
				}catch(e){
					signal(error, module.result = makeError("factoryThrew", [module, e]));
				}
			}else{
				result= isFunction(factory) ? factory.apply(null, args) : factory;
			}
			module.result = result===undefined && module.cjs ? module.cjs.exports : result;
			1 && syncExecStack.shift(module);
		},

		abortExec = {},

		defOrder = 0,

		promoteModuleToPlugin = function(pluginModule){
			var plugin = pluginModule.result;
			pluginModule.dynamic = plugin.dynamic;
			pluginModule.normalize = plugin.normalize;
			pluginModule.load = plugin.load;
			return pluginModule;
		},

		resolvePluginLoadQ = function(plugin){
			

			
			
			
			var map = {};
			forEach(plugin.loadQ, function(pseudoPluginResource){
				
				var pseudoMid = pseudoPluginResource.mid,
					prid = resolvePluginResourceId(plugin, pseudoPluginResource.prid, pseudoPluginResource.req.module),
					mid = plugin.dynamic ? pseudoPluginResource.mid.replace(/waitingForPlugin$/, prid) : (plugin.mid + "!" + prid),
					pluginResource = mix(mix({}, pseudoPluginResource), {mid:mid, prid:prid, injected:0});
				if(!modules[mid]){
					
					injectPlugin(modules[mid] = pluginResource);
				} 

				
				
				map[pseudoPluginResource.mid] = modules[mid];
				setArrived(pseudoPluginResource);
				delete modules[pseudoPluginResource.mid];
			});
			plugin.loadQ = 0;

			
			var substituteModules = function(module){
				for(var replacement, deps = module.deps || [], i = 0; i<deps.length; i++){
					replacement = map[deps[i].mid];
					if(replacement){
						deps[i] = replacement;
					}
				}
			};
			for(var p in modules){
				substituteModules(modules[p]);
			}
			forEach(execQ, substituteModules);
		},

		finishExec = function(module){
			req.trace("loader-finish-exec", [module.mid]);
			module.executed = executed;
			module.defOrder = defOrder++;
			1 && forEach(module.provides, function(cb){ cb(); });
			if(module.loadQ){
				
				promoteModuleToPlugin(module);
				resolvePluginLoadQ(module);
			}
			
			for(i = 0; i < execQ.length;){
				if(execQ[i] === module){
					execQ.splice(i, 1);
				}else{
					i++;
				}
			}
		},

		circleTrace = [],

		execModule = function(module, strict){
			
			if(module.executed === executing){
				req.trace("loader-circular-dependency", [circleTrace.concat(mid).join("->")]);
				return (!module.def || strict) ? abortExec :  (module.cjs && module.cjs.exports);
			}
			


			if(!module.executed){
				if(!module.def){
					return abortExec;
				}
				var mid = module.mid,
					deps = module.deps || [],
					arg, argResult,
					args = [],
					i = 0;

				if(0){
					circleTrace.push(mid);
					req.trace("loader-exec-module", ["exec", circleTrace.length, mid]);
				}

				
				
				
				
				
				
				module.executed = executing;
				while(i < deps.length){
					arg = deps[i++];
					argResult = ((arg === cjsRequireModule) ? createRequire(module) :
									((arg === cjsExportsModule) ? module.cjs.exports :
										((arg === cjsModuleModule) ? module.cjs :
											execModule(arg, strict))));
					if(argResult === abortExec){
						module.executed = 0;
						req.trace("loader-exec-module", ["abort", mid]);
						0 && circleTrace.pop();
						return abortExec;
					}
					args.push(argResult);
				}
				runFactory(module, args);
				finishExec(module);
			}
			

			0 && circleTrace.pop();
			return module.result;
		},


		checkCompleteGuard =  0,

		checkComplete = function(){
			
			
			if(checkCompleteGuard){
				return;
			}
			checkCompleteGuard++;
			checkDojoRequirePlugin();
			for(var currentDefOrder, module, i = 0; i < execQ.length;){
				currentDefOrder = defOrder;
				module = execQ[i];
				execModule(module);
				if(currentDefOrder!=defOrder){
					
					
					checkDojoRequirePlugin();
					i = 0;
				}else{
					
					i++;
				}
			}
			checkIdle();
		},

		checkIdle = function(){
			checkCompleteGuard--;
			if(execComplete()){
				signal("idle", []);
			}
		};


	if(0){
		req.undef = function(moduleId, referenceModule){
			
			
			var module = getModule(moduleId, referenceModule);
			setArrived(module);
			delete modules[module.mid];
		};
	}

	if(1){
		var fixupUrl= function(url){
				url += ""; 
				return url + (cacheBust ? ((/\?/.test(url) ? "&" : "?") + cacheBust) : "");
			},

			injectPlugin = function(
				module
			){
				
				var plugin = module.plugin;

				if(plugin.executed === executed && !plugin.load){
					
					promoteModuleToPlugin(plugin);
				}

				var onLoad = function(def){
						module.result = def;
						setArrived(module);
						finishExec(module);
						checkComplete();
					};

				setRequested(module);
				if(plugin.load){
					plugin.load(module.prid, module.req, onLoad);
				}else if(plugin.loadQ){
					plugin.loadQ.push(module);
				}else{
					
					
					
					
					execQ.unshift(plugin);
					injectModule(plugin);

					
					if(plugin.load){
						plugin.load(module.prid, module.req, onLoad);
					}else{
						
						plugin.loadQ = [module];
					}
				}
			},

			

			cached = 0,

			injectingModule = 0,

			injectingCachedModule = 0,

			evalModuleText = function(text, module){
				
				injectingCachedModule = 1;
				if(has("config-dojo-loader-catches")){
					try{
						if(text===cached){
							cached.call(null);
						}else{
							req.eval(text, module.mid);
						}
					}catch(e){
						signal(error, makeError("evalModuleThrew", module));
					}
				}else{
					if(text===cached){
						cached.call(null);
					}else{
						req.eval(text, module.mid);
					}
				}
				injectingCachedModule = 0;
			},

			injectModule = function(module){
				
				
				
				

				var mid = module.mid,
					url = module.url;
				if(module.executed || module.injected || waiting[mid] || (module.url && ((module.pack && waiting[module.url]===module.pack) || waiting[module.url]==1))){
					return;
				}

				if(0){
					var viaCombo = 0;
					if(module.plugin && module.plugin.isCombo){
						
						
						
						
						req.combo.add(module.plugin.mid, module.prid, 0, req);
						viaCombo = 1;
					}else if(!module.plugin){
						viaCombo = req.combo.add(0, module.mid, module.url, req);
					}
					if(viaCombo){
						setRequested(module);
						comboPending= 1;
						return;
					}
				}

				if(module.plugin){
					injectPlugin(module);
					return;
				} 

				setRequested(module);

				var onLoadCallback = function(){
					runDefQ(module);
					if(module.injected !== arrived){
						
						
						
						
						setArrived(module);
						mix(module, nonModuleProps);
					}

					if(1 && legacyMode){
						
						
						
						!syncExecStack.length && checkComplete();
					}else{
						checkComplete();
					}
				};
				cached = cache[mid] || cache[module.cacheId];
				if(cached){
					req.trace("loader-inject", ["cache", module.mid, url]);
					evalModuleText(cached, module);
					onLoadCallback();
					return;
				}
				if(1 && legacyMode){
					if(module.isXd){
						
						legacyMode==sync && (legacyMode = xd);
						
					}else if(module.isAmd && legacyMode!=sync){
						
					}else{
						
						var xhrCallback = function(text){
							if(legacyMode==sync){
								
								
								
								
								syncExecStack.unshift(module);
								evalModuleText(text, module);
								syncExecStack.shift();

								
								runDefQ(module);

								
								if(!module.cjs){
									setArrived(module);
									finishExec(module);
								}

								if(module.finish){
									
									
									
									
									

									
									
									var finishMid = mid + "*finish",
										finish = module.finish;
									delete module.finish;

									def(finishMid, ["dojo", ("dojo/require!" + finish.join(",")).replace(/\./g, "/")], function(dojo){
										forEach(finish, function(mid){ dojo.require(mid); });
									});
									
									execQ.unshift(getModule(finishMid));
								}
								onLoadCallback();
							}else{
								text = transformToAmd(module, text);
								if(text){
									evalModuleText(text, module);
									onLoadCallback();
								}else{
									
									
									injectingModule = module;
									req.injectUrl(fixupUrl(url), onLoadCallback, module);
									injectingModule = 0;
								}
							}
						};

						req.trace("loader-inject", ["xhr", module.mid, url, legacyMode!=sync]);
						if(has("config-dojo-loader-catches")){
							try{
								req.getText(url, legacyMode!=sync, xhrCallback);
							}catch(e){
								signal(error, makeError("xhrInjectFailed", [module, e]));
							}
						}else{
							req.getText(url, legacyMode!=sync, xhrCallback);
						}
						return;
					}
				} 
				req.trace("loader-inject", ["script", module.mid, url]);
				injectingModule = module;
				req.injectUrl(fixupUrl(url), onLoadCallback, module);
				injectingModule = 0;
			},

			defineModule = function(module, deps, def){
				req.trace("loader-define-module", [module.mid, deps]);

				if(0 && module.plugin && module.plugin.isCombo){
					
					
					
					module.result = isFunction(def) ? def() : def;
					setArrived(module);
					finishExec(module);
					return module;
				};

				var mid = module.mid;
				if(module.injected === arrived){
					signal(error, makeError("multipleDefine", module));
					return module;
				}
				mix(module, {
					deps: deps,
					def: def,
					cjs: {
						id: module.mid,
						uri: module.url,
						exports: (module.result = {}),
						setExports: function(exports){
							module.cjs.exports = exports;
						}
					}
				});

				
				for(var i = 0; i < deps.length; i++){
					deps[i] = getModule(deps[i], module);
				}

				if(1 && legacyMode && !waiting[mid]){
					
					injectDependencies(module);
					execQ.push(module);
					checkComplete();
				}
				setArrived(module);

				if(!isFunction(def) && !deps.length){
					module.result = def;
					finishExec(module);
				}

				return module;
			},

			runDefQ = function(referenceModule, mids){
				
				
				consumePendingCacheInsert(referenceModule);
				var definedModules = [],
					module, args;
				while(defQ.length){
					args = defQ.shift();
					mids && (args[0]= mids.shift());
					
					
					
					module = args[0] && getModule(args[0]) || referenceModule;
					definedModules.push(defineModule(module, args[1], args[2]));
				}
				forEach(definedModules, injectDependencies);
			};
	}

	var timerId = 0,
		clearTimer = noop,
		startTimer = noop;
	if(1){
		
		clearTimer = function(){
			timerId && clearTimeout(timerId);
			timerId = 0;
		},

		startTimer = function(){
			clearTimer();
			req.waitms && (timerId = setTimeout(function(){
				clearTimer();
				signal(error, makeError("timeout", waiting));
			}, req.waitms));
		};
	}

	if(1){
		has.add("ie-event-behavior", doc.attachEvent && (typeof opera === "undefined" || opera.toString() != "[object Opera]"));
	}

	if(1 && (1 || 1)){
		var domOn = function(node, eventName, ieEventName, handler){
				
				
				if(!has("ie-event-behavior")){
					node.addEventListener(eventName, handler, false);
					return function(){
						node.removeEventListener(eventName, handler, false);
					};
				}else{
					node.attachEvent(ieEventName, handler);
					return function(){
						node.detachEvent(ieEventName, handler);
					};
				}
			},
			windowOnLoadListener = domOn(window, "load", "onload", function(){
				req.pageLoaded = 1;
				doc.readyState!="complete" && (doc.readyState = "complete");
				windowOnLoadListener();
			});

		if(1){
			
			
			
			
			var sibling = doc.getElementsByTagName("script")[0],
				insertPoint= sibling.parentNode;
			req.injectUrl = function(url, callback, owner){
				
				

				startTimer();
				var node = owner.node = doc.createElement("script"),
					onLoad = function(e){
						e = e || window.event;
						var node = e.target || e.srcElement;
						if(e.type === "load" || /complete|loaded/.test(node.readyState)){
							disconnector();
							callback && callback();
						}
					},
					disconnector = domOn(node, "load", "onreadystatechange", onLoad);
				node.type = "text/javascript";
				node.charset = "utf-8";
				node.src = url;
				insertPoint.insertBefore(node, sibling);
				return node;
			};
		}
	}

	if(1){
		req.log = function(){
			try{
				for(var i = 0; i < arguments.length; i++){
					console.log(arguments[i]);
				}
			}catch(e){}
		};
	}else{
		req.log = noop;
	}

	if(0){
		var trace = req.trace = function(
			group,	
			args	
		){
			/
			
			
			

			if(trace.on && trace.group[group]){
				signal("trace", [group, args]);
				for(var arg, dump = [], text= "trace:" + group + (args.length ? (":" + args[0]) : ""), i= 1; i<args.length;){
					arg = args[i++];
					if(isString(arg)){
						text += ", " + arg;
					}else{
						dump.push(arg);
					}
				}
				req.log(text);
				dump.length && dump.push(".");
				req.log.apply(req, dump);
			}
		};
		mix(trace, {
			on:1,
			group:{},
			set:function(group, value){
				if(isString(group)){
					trace.group[group]= value;
				}else{
					mix(trace.group, group);
				}
			}
		});
		trace.set(mix(mix(mix({}, defaultConfig.trace), userConfig.trace), dojoSniffConfig.trace));
		on("config", function(config){
			config.trace && trace.set(config.trace);
		});
	}else{
		req.trace = noop;
	}

	var def = function(
		mid,		  
		dependencies, 
		factory		  
	){
		/
		// Advises the loader of a module factory. //Implements http:
		/
		
		// CommonJS factory scan courtesy of http:

		var arity = arguments.length,
			args = 0,
			defaultDeps = ["require", "exports", "module"];

		if(0){
			if(arity == 1 && isFunction(mid)){
				dependencies = [];
				mid.toString()
					.replace(/(\/\*([\s\S]*?)\*\/|\/\/(.*)$)/mg, "")
					.replace(/require\(["']([\w\!\-_\.\/]+)["']\)/g, function (match, dep){
					dependencies.push(dep);
				});
				args = [0, defaultDeps.concat(dependencies), mid];
			}
		}
		if(!args){
			args = arity == 1 ? [0, defaultDeps, mid] :
				(arity == 2 ? (isArray(mid) ? [0, mid, dependencies] : (isFunction(dependencies) ? [mid, defaultDeps, dependencies] : [mid, [], dependencies])) :
					[mid, dependencies, factory]);
		}
		req.trace("loader-define", args.slice(0, 2));
		var targetModule = args[0] && getModule(args[0]),
			module;
		if(targetModule && !waiting[targetModule.mid]){
			
			
			
			
			
			injectDependencies(defineModule(targetModule, args[1], args[2]));
		}else if(!has("ie-event-behavior") || !1 || injectingCachedModule){
			
			
			defQ.push(args);
		}else{
			
			
			targetModule = targetModule || injectingModule;
			if(!targetModule){
				for(mid in waiting){
					module = modules[mid];
					if(module && module.node && module.node.readyState === 'interactive'){
						targetModule = module;
						break;
					}
				}
				if(0 && !targetModule){
					for(var i = 0; i<combosPending.length; i++){
						targetModule = combosPending[i];
						if(targetModule.node && targetModule.node.readyState === 'interactive'){
							break;
						}
						targetModule= 0;
					}
				}
			}
			if(0 && isArray(targetModule)){
				injectDependencies(defineModule(targetModule.shift(), args[1], args[2]));
				if(!targetModule.length){
					combosPending.splice(i, 1);
				}
			}else if(targetModule){
				consumePendingCacheInsert(targetModule);
				injectDependencies(defineModule(targetModule, args[1], args[2]));
			}else{
				signal(error, makeError("ieDefineFailed", args[0]));
			}
			checkComplete();
		}
	};
	def.amd = {
		vendor:"dojotoolkit.org"
	};

	if(0){
		req.def = def;
	}

	
	
	
	mix(mix(req, defaultConfig.loaderPatch), userConfig.loaderPatch);

	
	on(error, function(arg){
		try{
			console.error(arg);
			if(arg instanceof Error){
				for(var p in arg){
					console.log(p + ":", arg[p]);
				}
				console.log(".");
			}
		}catch(e){}
	});

	
	mix(req, {
		uid:uid,
		cache:cache,
		packs:packs
	});


	if(0){
		mix(req, {
			
			paths:paths,
			aliases:aliases,
			packageMap:packageMap,
			modules:modules,
			legacyMode:legacyMode,
			execQ:execQ,
			defQ:defQ,
			waiting:waiting,

			
			
			pathsMapProg:pathsMapProg,
			packageMapProg:packageMapProg,
			listenerQueues:listenerQueues,

			
			computeMapProg:computeMapProg,
			runMapProg:runMapProg,
			compactPath:compactPath,
			getModuleInfo:getModuleInfo_
		});
	}

	
	
	if(global.define){
		if(1){
			signal(error, makeError("defineAlreadyDefined", 0));
		}
	}else{
		global.define = def;
		global.require = req;
	}

	if(0 && req.combo && req.combo.plugins){
		var plugins = req.combo.plugins,
			pluginName;
		for(pluginName in plugins){
			mix(mix(getModule(pluginName), plugins[pluginName]), {isCombo:1, executed:"executed", load:1});
		}
	}

	if(1){
		var bootDeps = defaultConfig.deps || userConfig.deps || dojoSniffConfig.deps,
			bootCallback = defaultConfig.deps || userConfig.callback || dojoSniffConfig.callback;
		req.boot = (bootDeps || bootCallback) ? [bootDeps || [], bootCallback] : 0;
	}
	if(!1){
		!req.async && req(["dojo"]);
		req.boot && req.apply(null, req.boot);
	}
})
(this.dojoConfig || this.djConfig || this.require || {}, {
		async:0,
		hasCache:{
				'config-selectorEngine':"acme",
				'dojo-built':1,
				'dojo-loader':1,
				dom:1,
				'host-browser':1
		},
		packages:[
				{
					 location:"../dijit",
					 name:"dijit"
				},
				{
					 location:"../dojox",
					 name:"dojox"
				},
				{
					 location:".",
					 name:"dojo"
				}
		]
});require({cache:{
'dojo/_base/fx':function(){
define(["./kernel", "./lang", "../Evented", "./Color", "./connect", "./sniff", "../dom", "../dom-style"], function(dojo, lang, Evented, Color, connect, has, dom, style){
	
	
	
	
	
	
	//		http:

	var _mixin = lang.mixin;

	dojo._Line = function(/*int*/ start, /*int*/ end){
		
		
		
		
		
		
		
		this.start = start;
		this.end = end;
	};

	dojo._Line.prototype.getValue = function(/*float*/ n){
		
		
		return ((this.end - this.start) * n) + this.start; 
	};

	dojo.Animation = function(args){
		
		
		
		
		
		
		
		
		
		
		
		
		

		_mixin(this, args);
		if(lang.isArray(this.curve)){
			this.curve = new dojo._Line(this.curve[0], this.curve[1]);
		}

	};
	dojo.Animation.prototype = new Evented();
	
	dojo._Animation = dojo.Animation;

	lang.extend(dojo.Animation, {
		
		
		duration: 350,

	/*=====
		
		
		
		curve: null,

		
		
		
		easing: null,
	=====*/

		
		
		repeat: 0,

		
		
		
		rate: 20 /* 50 fps */,

	/*=====
		
		
		
		delay: null,

		
		
		beforeBegin: null,

		
		
		onBegin: null,

		
		
		onAnimate: null,

		
		
		onEnd: null,

		
		
		onPlay: null,

		
		
		onPause: null,

		
		
		onStop: null,

	=====*/

		_percent: 0,
		_startRepeatCount: 0,

		_getStep: function(){
			var _p = this._percent,
				_e = this.easing
			;
			return _e ? _e(_p) : _p;
		},
		_fire: function(/*Event*/ evt, /*Array?*/ args){
			
			
			
			
			
			
			
			
			
			
			
			
			var a = args||[];
			if(this[evt]){
				if(dojo.config.debugAtAllCosts){
					this[evt].apply(this, a);
				}else{
					try{
						this[evt].apply(this, a);
					}catch(e){
						
						
						
						
						
						console.error("exception in animation handler for:", evt);
						console.error(e);
					}
				}
			}
			return this; 
		},

		play: function(/*int?*/ delay, /*Boolean?*/ gotoStart){
			
			
			
			
			
			
			
			
			

			var _t = this;
			if(_t._delayTimer){ _t._clearTimer(); }
			if(gotoStart){
				_t._stopTimer();
				_t._active = _t._paused = false;
				_t._percent = 0;
			}else if(_t._active && !_t._paused){
				return _t;
			}

			_t._fire("beforeBegin", [_t.node]);

			var de = delay || _t.delay,
				_p = lang.hitch(_t, "_play", gotoStart);

			if(de > 0){
				_t._delayTimer = setTimeout(_p, de);
				return _t;
			}
			_p();
			return _t;	
		},

		_play: function(gotoStart){
			var _t = this;
			if(_t._delayTimer){ _t._clearTimer(); }
			_t._startTime = new Date().valueOf();
			if(_t._paused){
				_t._startTime -= _t.duration * _t._percent;
			}

			_t._active = true;
			_t._paused = false;
			var value = _t.curve.getValue(_t._getStep());
			if(!_t._percent){
				if(!_t._startRepeatCount){
					_t._startRepeatCount = _t.repeat;
				}
				_t._fire("onBegin", [value]);
			}

			_t._fire("onPlay", [value]);

			_t._cycle();
			return _t; 
		},

		pause: function(){
			
			var _t = this;
			if(_t._delayTimer){ _t._clearTimer(); }
			_t._stopTimer();
			if(!_t._active){ return _t; /*dojo.Animation*/ }
			_t._paused = true;
			_t._fire("onPause", [_t.curve.getValue(_t._getStep())]);
			return _t; 
		},

		gotoPercent: function(/*Decimal*/ percent, /*Boolean?*/ andPlay){
			
			
			
			
			
			
			var _t = this;
			_t._stopTimer();
			_t._active = _t._paused = true;
			_t._percent = percent;
			if(andPlay){ _t.play(); }
			return _t; 
		},

		stop: function(/*boolean?*/ gotoEnd){
			
			
			var _t = this;
			if(_t._delayTimer){ _t._clearTimer(); }
			if(!_t._timer){ return _t; /* dojo.Animation */ }
			_t._stopTimer();
			if(gotoEnd){
				_t._percent = 1;
			}
			_t._fire("onStop", [_t.curve.getValue(_t._getStep())]);
			_t._active = _t._paused = false;
			return _t; 
		},

		status: function(){
			
			
			
			if(this._active){
				return this._paused ? "paused" : "playing"; 
			}
			return "stopped"; 
		},

		_cycle: function(){
			var _t = this;
			if(_t._active){
				var curr = new Date().valueOf();
				var step = (curr - _t._startTime) / (_t.duration);

				if(step >= 1){
					step = 1;
				}
				_t._percent = step;

				
				if(_t.easing){
					step = _t.easing(step);
				}

				_t._fire("onAnimate", [_t.curve.getValue(step)]);

				if(_t._percent < 1){
					_t._startTimer();
				}else{
					_t._active = false;

					if(_t.repeat > 0){
						_t.repeat--;
						_t.play(null, true);
					}else if(_t.repeat == -1){
						_t.play(null, true);
					}else{
						if(_t._startRepeatCount){
							_t.repeat = _t._startRepeatCount;
							_t._startRepeatCount = 0;
						}
					}
					_t._percent = 0;
					_t._fire("onEnd", [_t.node]);
					!_t.repeat && _t._stopTimer();
				}
			}
			return _t; 
		},

		_clearTimer: function(){
			
			clearTimeout(this._delayTimer);
			delete this._delayTimer;
		}

	});

	
	var ctr = 0,
		timer = null,
		runner = {
			run: function(){}
		};

	lang.extend(dojo.Animation, {

		_startTimer: function(){
			if(!this._timer){
				this._timer = connect.connect(runner, "run", this, "_cycle");
				ctr++;
			}
			if(!timer){
				timer = setInterval(lang.hitch(runner, "run"), this.rate);
			}
		},

		_stopTimer: function(){
			if(this._timer){
				connect.disconnect(this._timer);
				this._timer = null;
				ctr--;
			}
			if(ctr <= 0){
				clearInterval(timer);
				timer = null;
				ctr = 0;
			}
		}

	});

	var _makeFadeable =
				has("ie") ? function(node){
			
			
			var ns = node.style;
			
			
			if(!ns.width.length && style.get(node, "width") == "auto"){
				ns.width = "auto";
			}
		} :
				function(){};

	dojo._fade = function(/*Object*/ args){
		
		
		
		

		args.node = dom.byId(args.node);
		var fArgs = _mixin({ properties: {} }, args),
			props = (fArgs.properties.opacity = {});

		props.start = !("start" in fArgs) ?
			function(){
				return +style.get(fArgs.node, "opacity")||0;
			} : fArgs.start;
		props.end = fArgs.end;

		var anim = dojo.animateProperty(fArgs);
		connect.connect(anim, "beforeBegin", lang.partial(_makeFadeable, fArgs.node));

		return anim; 
	};

	/*=====
	dojo.__FadeArgs = function(node, duration, easing){
		
		
		
		
		
		
		this.node = node;
		this.duration = duration;
		this.easing = easing;
	}
	=====*/

	dojo.fadeIn = function(/*dojo.__FadeArgs*/ args){
		
		
		
		return dojo._fade(_mixin({ end: 1 }, args)); 
	};

	dojo.fadeOut = function(/*dojo.__FadeArgs*/ args){
		
		
		
		return dojo._fade(_mixin({ end: 0 }, args)); 
	};

	dojo._defaultEasing = function(/*Decimal?*/ n){
		
		return 0.5 + ((Math.sin((n + 1.5) * Math.PI)) / 2);	
	};

	var PropLine = function(properties){
		
		
		
		
		this._properties = properties;
		for(var p in properties){
			var prop = properties[p];
			if(prop.start instanceof Color){
				
				prop.tempColor = new Color();
			}
		}
	};

	PropLine.prototype.getValue = function(r){
		var ret = {};
		for(var p in this._properties){
			var prop = this._properties[p],
				start = prop.start;
			if(start instanceof Color){
				ret[p] = Color.blendColors(start, prop.end, r, prop.tempColor).toCss();
			}else if(!lang.isArray(start)){
				ret[p] = ((prop.end - start) * r) + start + (p != "opacity" ? prop.units || "px" : 0);
			}
		}
		return ret;
	};

	/*=====
	dojo.declare("dojo.__AnimArgs", [dojo.__FadeArgs], {
		
		
		
		properties: {}

		
	});
	=====*/

	dojo.animateProperty = function(/*dojo.__AnimArgs*/ args){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|		
		
		
		
		
		
		//	|			
		//	|			
		
		//	|	}).play(500); 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
		
		
		
		
		
		
		
		
		
		//	|				
		
		
		
		
		
		
		
		
		

		var n = args.node = dom.byId(args.node);
		if(!args.easing){ args.easing = dojo._defaultEasing; }

		var anim = new dojo.Animation(args);
		connect.connect(anim, "beforeBegin", anim, function(){
			var pm = {};
			for(var p in this.properties){
				
				
				
				
				if(p == "width" || p == "height"){
					this.node.display = "block";
				}
				var prop = this.properties[p];
				if(lang.isFunction(prop)){
					prop = prop(n);
				}
				prop = pm[p] = _mixin({}, (lang.isObject(prop) ? prop: { end: prop }));

				if(lang.isFunction(prop.start)){
					prop.start = prop.start(n);
				}
				if(lang.isFunction(prop.end)){
					prop.end = prop.end(n);
				}
				var isColor = (p.toLowerCase().indexOf("color") >= 0);
				function getStyle(node, p){
					
					var v = { height: node.offsetHeight, width: node.offsetWidth }[p];
					if(v !== undefined){ return v; }
					v = style.get(node, p);
					return (p == "opacity") ? +v : (isColor ? v : parseFloat(v));
				}
				if(!("end" in prop)){
					prop.end = getStyle(n, p);
				}else if(!("start" in prop)){
					prop.start = getStyle(n, p);
				}

				if(isColor){
					prop.start = new Color(prop.start);
					prop.end = new Color(prop.end);
				}else{
					prop.start = (p == "opacity") ? +prop.start : parseFloat(prop.start);
				}
			}
			this.curve = new PropLine(pm);
		});
		connect.connect(anim, "onAnimate", lang.hitch(style, "set", anim.node));
		return anim; 
	};

	dojo.anim = function(	/*DOMNode|String*/	node,
							/*Object*/			properties,
							/*Integer?*/		duration,
							/*Function?*/		easing,
							/*Function?*/		onEnd,
							/*Integer?*/		delay){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return dojo.animateProperty({ 
			node: node,
			duration: duration || dojo.Animation.prototype.duration,
			properties: properties,
			easing: easing,
			onEnd: onEnd
		}).play(delay || 0);
	};

	return {
		_Line: dojo._Line,
		Animation: dojo.Animation,
		_fade: dojo._fade,
		fadeIn: dojo.fadeIn,
		fadeOut: dojo.fadeOut,
		_defaultEasing: dojo._defaultEasing,
		animateProperty: dojo.animateProperty,
		anim: dojo.anim
	};
});

},
'dojo/dom-form':function(){
define("dojo/dom-form", ["./_base/lang", "./dom", "./io-query", "./json"], function(lang, dom, ioq, json){
	
	
	
	

	/*=====
	dojo.fieldToObject = function(inputNode){
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
    dojo.formToObject = function(formNode){
        
        
        
        
        
        
        
		
		
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    };
	=====*/

	/*=====
    dojo.formToQuery = function(formNode){
        
        
        
		
		
    };
	=====*/

	/*=====
    dojo.formToJson = function(formNode, prettyPrint){
        
        
        
		
		
		
    };
	=====*/

    function setValue(/*Object*/obj, /*String*/name, /*String*/value){
        
        
        
        

        
        if(value === null){
            return;
        }

        var val = obj[name];
        if(typeof val == "string"){ 
            obj[name] = [val, value];
        }else if(lang.isArray(val)){
            val.push(value);
        }else{
            obj[name] = value;
        }
    }

	var exclude = "file|submit|image|reset|button";

	var form = {
		fieldToObject: function fieldToObject(/*DOMNode|String*/ inputNode){
			var ret = null;
			inputNode = dom.byId(inputNode);
			if(inputNode){
				var _in = inputNode.name, type = (inputNode.type || "").toLowerCase();
				if(_in && type && !inputNode.disabled){
					if(type == "radio" || type == "checkbox"){
						if(inputNode.checked){
							ret = inputNode.value;
						}
					}else if(inputNode.multiple){
						ret = [];
						var nodes = [inputNode.firstChild];
						while(nodes.length){
							for(var node = nodes.pop(); node; node = node.nextSibling){
								if(node.nodeType == 1 && node.tagName.toLowerCase() == "option"){
									if(node.selected){
										ret.push(node.value);
									}
								}else{
									if(node.nextSibling){
										nodes.push(node.nextSibling);
									}
									if(node.firstChild){
										nodes.push(node.firstChild);
									}
									break;
								}
							}
						}
					}else{
						ret = inputNode.value;
					}
				}
			}
			return ret; 
		},

		toObject: function formToObject(/*DOMNode|String*/ formNode){
			var ret = {}, elems = dom.byId(formNode).elements;
			for(var i = 0, l = elems.length; i < l; ++i){
				var item = elems[i], _in = item.name, type = (item.type || "").toLowerCase();
				if(_in && type && exclude.indexOf(type) < 0 && !item.disabled){
					setValue(ret, _in, form.fieldToObject(item));
					if(type == "image"){
						ret[_in + ".x"] = ret[_in + ".y"] = ret[_in].x = ret[_in].y = 0;
					}
				}
			}
			return ret; 
		},

		toQuery: function formToQuery(/*DOMNode|String*/ formNode){
			return ioq.objectToQuery(form.toObject(formNode)); 
		},

		toJson: function formToJson(/*DOMNode|String*/ formNode, /*Boolean?*/prettyPrint){
			return json.stringify(form.toObject(formNode), null, prettyPrint ? 4 : 0); 
		}
	};

    return form;
});

},
'dojo/_base/html':function(){
define(["./kernel", "../dom", "../dom-style", "../dom-attr", "../dom-prop", "../dom-class", "../dom-construct", "../dom-geometry"], function(dojo, dom, style, attr, prop, cls, ctr, geom){
	
	
	
	

	
	dojo.byId = dom.byId;
	dojo.isDescendant = dom.isDescendant;
	dojo.setSelectable = dom.setSelectable;

	
	dojo.getAttr = attr.get;
	dojo.setAttr = attr.set;
	dojo.hasAttr = attr.has;
	dojo.removeAttr = attr.remove;
	dojo.getNodeProp = attr.getNodeProp;

	dojo.attr = function(node, name, value){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
		//	|	
		
		
		
		//	|	
		
		
		
		
		
		
		
		
		
		
		//	|			
		//	|			
		//	|			
		//	|			
		//	|			
		//	|			
		
		
		//	|			
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		

		if(arguments.length == 2){
			return attr[typeof name == "string" ? "get" : "set"](node, name);
		}
		return attr.set(node, name, value);
	};

	
	dojo.hasClass = cls.contains;
	dojo.addClass = cls.add;
	dojo.removeClass = cls.remove;
	dojo.toggleClass = cls.toggle;
	dojo.replaceClass = cls.replace;

	
	dojo._toDom = dojo.toDom = ctr.toDom;
	dojo.place = ctr.place;
	dojo.create = ctr.create;
	dojo.empty = function(node){ ctr.empty(node); };
	dojo._destroyElement = dojo.destroy = function(node){ ctr.destroy(node); };

	
	dojo._getPadExtents = dojo.getPadExtents = geom.getPadExtents;
	dojo._getBorderExtents = dojo.getBorderExtents = geom.getBorderExtents;
	dojo._getPadBorderExtents = dojo.getPadBorderExtents = geom.getPadBorderExtents;
	dojo._getMarginExtents = dojo.getMarginExtents = geom.getMarginExtents;
	dojo._getMarginSize = dojo.getMarginSize = geom.getMarginSize;
	dojo._getMarginBox = dojo.getMarginBox = geom.getMarginBox;
	dojo.setMarginBox = geom.setMarginBox;
	dojo._getContentBox = dojo.getContentBox = geom.getContentBox;
	dojo.setContentSize = geom.setContentSize;
	dojo._isBodyLtr = dojo.isBodyLtr = geom.isBodyLtr;
	dojo._docScroll = dojo.docScroll = geom.docScroll;
	dojo._getIeDocumentElementOffset = dojo.getIeDocumentElementOffset = geom.getIeDocumentElementOffset;
	dojo._fixIeBiDiScrollLeft = dojo.fixIeBiDiScrollLeft = geom.fixIeBiDiScrollLeft;
	dojo.position = geom.position;

	dojo.marginBox = function marginBox(/*DomNode|String*/node, /*Object?*/box){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return box ? geom.setMarginBox(node, box) : geom.getMarginBox(node); 
	};

	dojo.contentBox = function contentBox(/*DomNode|String*/node, /*Object?*/box){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return box ? geom.setContentSize(node, box) : geom.getContentBox(node); 
	};

	dojo.coords = function(/*DomNode|String*/node, /*Boolean?*/includeScroll){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		dojo.deprecated("dojo.coords()", "Use dojo.position() or dojo.marginBox().");
		node = dom.byId(node);
		var s = style.getComputedStyle(node), mb = geom.getMarginBox(node, s);
		var abs = geom.position(node, includeScroll);
		mb.x = abs.x;
		mb.y = abs.y;
		return mb;	
	};

	
	dojo.getProp = prop.get;
	dojo.setProp = prop.set;

	dojo.prop = function(/*DomNode|String*/node, /*String|Object*/name, /*String?*/value){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
		//	|	
		
		
		
		//	|	
		
		
		
		
		
		
		
		
		
		
		//	|			
		//	|			
		//	|			
		//	|			
		//	|			
		//	|			
		
		
		//	|			
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		

		if(arguments.length == 2){
			return prop[typeof name == "string" ? "get" : "set"](node, name);
		}
		
		return prop.set(node, name, value);
	};

	
	dojo.getStyle = style.get;
	dojo.setStyle = style.set;
	dojo.getComputedStyle = style.getComputedStyle;
	dojo.__toPixelValue = dojo.toPixelValue = style.toPixelValue;

	dojo.style = function(node, name, value){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	dojo.style("thinger", "opacity"); 
		
		
		
		
		//	|	dojo.style("thinger", "opacity", 0.5); 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
		
		
		

		switch(arguments.length){
			case 1:
				return style.get(node);
			case 2:
				return style[typeof name == "string" ? "get" : "set"](node, name);
		}
		
		return style.set(node, name, value);
	};

	return dojo;
});

},
'dojo/_base/kernel':function(){
define(["../has", "./config", "require", "module"], function(has, config, require, module){
	
	
	
	
	var
		
		i, p,

		
		
		dijit = {},
		dojox = {},
		dojo = {
			
			config:config,
			global:this,
			dijit:dijit,
			dojox:dojox
		};


	
	
	
	
	
	
	
	
	// http:
	
	
	var scopeMap =
			
			
			{
				dojo:["dojo", dojo],
				dijit:["dijit", dijit],
				dojox:["dojox", dojox]
			},

		packageMap =
			
			(require.packs && require.packs[module.id.match(/[^\/]+/)[0]].packageMap) || {},

		item;

	
	for(p in packageMap){
		if(scopeMap[p]){
			
			scopeMap[p][0] = packageMap[p];
		}else{
			
			scopeMap[p] = [packageMap[p], {}];
		}
	}

	
	for(p in scopeMap){
		item = scopeMap[p];
		item[1]._scopeName = item[0];
		if(!config.noGlobals){
			this[item[0]] = item[1];
		}
	}
	dojo.scopeMap = scopeMap;

	
	dojo.baseUrl = dojo.config.baseUrl = require.baseUrl;
	dojo.isAsync = !1 || require.async;
	dojo.locale = config.locale;

	/*=====
		dojo.version = function(){
			
			
			
			
			
			
			
			
			
			
			
			
			this.major = 0;
			this.minor = 0;
			this.patch = 0;
			this.flag = "";
			this.revision = 0;
		}
	=====*/
	var rev = "$Rev: 27407 $".match(/\d+/);
	dojo.version = {
		major: 1, minor: 7, patch: 1, flag: "",
		revision: rev ? +rev[0] : NaN,
		toString: function(){
			var v = dojo.version;
			return v.major + "." + v.minor + "." + v.patch + v.flag + " (" + v.revision + ")";	
		}
	};


	
	
	
	
	true || has.add("extend-dojo", 1);

	if(1){
		dojo.eval = require.eval;
	}else{
		var eval_ =
			
			new Function("__text", "return eval(__text);");

		dojo.eval = function(text, hint){
			
			return eval_(text + "\r\n//
		};
	}

	if(0){
		dojo.exit = function(exitcode){
			quit(exitcode);
		};
	} else{
		dojo.exit = function(){
		};
	}

	true || has.add("dojo-guarantee-console",
		
		1
	);
	if(1){
		typeof console != "undefined" || (console = {});
		
		var cn = [
			"assert", "count", "debug", "dir", "dirxml", "error", "group",
			"groupEnd", "info", "profile", "profileEnd", "time", "timeEnd",
			"trace", "warn", "log"
		];
		var tn;
		i = 0;
		while((tn = cn[i++])){
			if(!console[tn]){
				(function(){
					var tcn = tn + "";
					console[tcn] = ('log' in console) ? function(){
						var a = Array.apply({}, arguments);
						a.unshift(tcn + ":");
						console["log"](a.join(" "));
					} : function(){};
					console[tcn]._fake = true;
				})();
			}
		}
	}

	has.add("dojo-debug-messages",
		
		!!config.isDebug
	);
	if(has("dojo-debug-messages")){
		dojo.deprecated = function(/*String*/ behaviour, /*String?*/ extra, /*String?*/ removal){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

			var message = "DEPRECATED: " + behaviour;
			if(extra){ message += " " + extra; }
			if(removal){ message += " -- will be removed in version: " + removal; }
			console.warn(message);
		};

		dojo.experimental = function(/* String */ moduleName, /* String? */ extra){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

			var message = "EXPERIMENTAL: " + moduleName + " -- APIs subject to change without notice.";
			if(extra){ message += " " + extra; }
			console.warn(message);
		};
	}else{
		dojo.deprecated = dojo.experimental =  function(){};
	}

	true || has.add("dojo-modulePaths",
		
		1
	);
	if(1){
		
		
		if(config.modulePaths){
			dojo.deprecated("dojo.modulePaths", "use paths configuration");
			var paths = {};
			for(p in config.modulePaths){
				paths[p.replace(/\./g, "/")] = config.modulePaths[p];
			}
			require({paths:paths});
		}
	}

	true || has.add("dojo-moduleUrl",
		
		1
	);
	if(1){
		dojo.moduleUrl = function(/*String*/module, /*String?*/url){
			
			
			
			
			//	|	console.dir(pngPath); 
			//	|	
			
			
			//	|	
			
			
			
			
			
			
			
			
			
			//	|	
			
			
			
			//	|	
			
			//	|	
			
			

			dojo.deprecated("dojo.moduleUrl()", "use require.toUrl", "2.0");

			
			
			
			
			var result = null;
			if(module){
				result = require.toUrl(module.replace(/\./g, "/") + (url ? ("/" + url) : "") + "/*.*").replace(/\/\*\.\*/, "") + (url ? "" : "/");
			}
			return result;
		};
	}

	dojo._hasResource = {}; 

	return dojo;
});

},
'dojo/io-query':function(){
define(["./_base/lang"], function(lang){
	
	
	
	

    var backstop = {};

    function objectToQuery(/*Object*/ map){
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

        
        var enc = encodeURIComponent, pairs = [];
        for(var name in map){
            var value = map[name];
            if(value != backstop[name]){
                var assign = enc(name) + "=";
                if(lang.isArray(value)){
                    for(var i = 0, l = value.length; i < l; ++i){
                        pairs.push(assign + enc(value[i]));
                    }
                }else{
                    pairs.push(assign + enc(value));
                }
            }
        }
        return pairs.join("&"); 
    }

    function queryToObject(/*String*/ str){
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

        
        var dec = decodeURIComponent, qp = str.split("&"), ret = {}, name, val;
        for(var i = 0, l = qp.length, item; i < l; ++i){
            item = qp[i];
            if(item.length){
                var s = item.indexOf("=");
                if(s < 0){
                    name = dec(item);
                    val = "";
                }else{
                    name = dec(item.slice(0, s));
                    val  = dec(item.slice(s + 1));
                }
                if(typeof ret[name] == "string"){ 
                    ret[name] = [ret[name]];
                }

                if(lang.isArray(ret[name])){
                    ret[name].push(val);
                }else{
                    ret[name] = val;
                }
            }
        }
        return ret; 
    }

    return {
        objectToQuery: objectToQuery,
        queryToObject: queryToObject
    };
});
},
'dojo/_base/Deferred':function(){
define("dojo/_base/Deferred", ["./kernel", "./lang"], function(dojo, lang){
	
	
	
	

	var mutator = function(){};
	var freeze = Object.freeze || function(){};
	
	dojo.Deferred = function(/*Function?*/ canceller){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//		|	
		
		
		
		
		
		
		
		
		
		
		
		
		
		//		|	
		
		//		|		
		
		
		
		
		//		|	
		
		
		
		
		
		
		
		
		//		|	
		
		
		
		
		
		
		
		
		
		
		
		
		
		//		|	
		
		
		
		//		|	
		//		|	
		//		|	
		
		
		
		
		
		//		|	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//		|	
		
		
		
		
		
		

		var result, finished, isError, head, nextListener;
		var promise = (this.promise = {});

		function complete(value){
			if(finished){
				throw new Error("This deferred has already been resolved");
			}
			result = value;
			finished = true;
			notify();
		}
		function notify(){
			var mutated;
			while(!mutated && nextListener){
				var listener = nextListener;
				nextListener = nextListener.next;
				if((mutated = (listener.progress == mutator))){ 
					finished = false;
				}
				var func = (isError ? listener.error : listener.resolved);
				if(func){
					try{
						var newResult = func(result);
						if (newResult && typeof newResult.then === "function"){
							newResult.then(lang.hitch(listener.deferred, "resolve"), lang.hitch(listener.deferred, "reject"), lang.hitch(listener.deferred, "progress"));
							continue;
						}
						var unchanged = mutated && newResult === undefined;
						if(mutated && !unchanged){
							isError = newResult instanceof Error;
						}
						listener.deferred[unchanged && isError ? "reject" : "resolve"](unchanged ? result : newResult);
					}catch(e){
						listener.deferred.reject(e);
					}
				}else{
					if(isError){
						listener.deferred.reject(result);
					}else{
						listener.deferred.resolve(result);
					}
				}
			}
		}
		
		this.resolve = this.callback = function(value){
			
			
			this.fired = 0;
			this.results = [value, null];
			complete(value);
		};


		
		this.reject = this.errback = function(error){
			
			
			isError = true;
			this.fired = 1;
			complete(error);
			this.results = [null, error];
			if(!error || error.log !== false){
				(dojo.config.deferredOnError || function(x){ console.error(x); })(error);
			}
		};
		
		this.progress = function(update){
			
			
			var listener = nextListener;
			while(listener){
				var progress = listener.progress;
				progress && progress(update);
				listener = listener.next;
			}
		};
		this.addCallbacks = function(callback, errback){
			
			
			
			
			
			
			
			
			this.then(callback, errback, mutator);
			return this;	
		};
		
		promise.then = this.then = function(/*Function?*/resolvedCallback, /*Function?*/errorCallback, /*Function?*/progressCallback){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			var returnDeferred = progressCallback == mutator ? this : new dojo.Deferred(promise.cancel);
			var listener = {
				resolved: resolvedCallback,
				error: errorCallback,
				progress: progressCallback,
				deferred: returnDeferred
			};
			if(nextListener){
				head = head.next = listener;
			}
			else{
				nextListener = head = listener;
			}
			if(finished){
				notify();
			}
			return returnDeferred.promise; 
		};
		var deferred = this;
		promise.cancel = this.cancel = function (){
			
			
			if(!finished){
				var error = canceller && canceller(deferred);
				if(!finished){
					if (!(error instanceof Error)){
						error = new Error(error);
					}
					error.log = false;
					deferred.reject(error);
				}
			}
		};
		freeze(promise);
	};
	lang.extend(dojo.Deferred, {
		addCallback: function (/*Function*/ callback){
			
			
			
			
			return this.addCallbacks(lang.hitch.apply(dojo, arguments));	
		},

		addErrback: function (/*Function*/ errback){
			
			
			
			
			return this.addCallbacks(null, lang.hitch.apply(dojo, arguments));	
		},

		addBoth: function (/*Function*/ callback){
			
			
			
			
			var enclosed = lang.hitch.apply(dojo, arguments);
			return this.addCallbacks(enclosed, enclosed);	
		},
		fired: -1
	});

	dojo.Deferred.when = dojo.when = function(promiseOrValue, /*Function?*/ callback, /*Function?*/ errback, /*Function?*/ progressHandler){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		if(promiseOrValue && typeof promiseOrValue.then === "function"){
			return promiseOrValue.then(callback, errback, progressHandler);
		}
		return callback ? callback(promiseOrValue) : promiseOrValue;	
	};

	return dojo.Deferred;
});

},
'dojo/NodeList-dom':function(){
define(["./_base/kernel", "./query", "./_base/array", "./_base/lang", "./dom-class", "./dom-construct", "./dom-geometry", "./dom-attr", "./dom-style"], function(dojo, query, array, lang, domCls, domCtr, domGeom, domAttr, domStyle){
	/*===== var NodeList = dojo.NodeList; =====*/
	var magicGuard = function(a){
		
		
		return a.length == 1 && (typeof a[0] == "string"); 
	};

	var orphan = function(node){
		
		
		var p = node.parentNode;
		if(p){
			p.removeChild(node);
		}
	};
	

	var NodeList = query.NodeList,
		awc = NodeList._adaptWithCondition,
		aafe = NodeList._adaptAsForEach,
		aam = NodeList._adaptAsMap;

	function getSet(module){
		return function(node, name, value){
			if(arguments.length == 2){
				return module[typeof name == "string" ? "get" : "set"](node, name);
			}
			
			return module.set(node, name, value);
		};
	}

	lang.extend(NodeList, {
		_normalize: function(/*String||Element||Object||NodeList*/content, /*DOMNode?*/refNode){
			
			
			
			
			
			
			
			
			
			
			

			
			
			
			
			

			var parse = content.parse === true;

			
			if(typeof content.template == "string"){
				var templateFunc = content.templateFunc || (dojo.string && dojo.string.substitute);
				content = templateFunc ? templateFunc(content.template, content) : content;
			}

			var type = (typeof content);
			if(type == "string" || type == "number"){
				content = domCtr.toDom(content, (refNode && refNode.ownerDocument));
				if(content.nodeType == 11){
					
					content = lang._toArray(content.childNodes);
				}else{
					content = [content];
				}
			}else if(!lang.isArrayLike(content)){
				content = [content];
			}else if(!lang.isArray(content)){
				
				
				content = lang._toArray(content);
			}

			
			if(parse){
				content._runParse = true;
			}
			return content; 
		},

		_cloneNode: function(/*DOMNode*/ node){
			
			
			
			
			return node.cloneNode(true);
		},

		_place: function(/*Array*/ary, /*DOMNode*/refNode, /*String*/position, /*Boolean*/useClone){
			
			
			
			
			

			
			if(refNode.nodeType != 1 && position == "only"){
				return;
			}
			var rNode = refNode, tempNode;

			
			
			var length = ary.length;
			for(var i = length - 1; i >= 0; i--){
				var node = (useClone ? this._cloneNode(ary[i]) : ary[i]);

				
				
				
				if(ary._runParse && dojo.parser && dojo.parser.parse){
					if(!tempNode){
						tempNode = rNode.ownerDocument.createElement("div");
					}
					tempNode.appendChild(node);
					dojo.parser.parse(tempNode);
					node = tempNode.firstChild;
					while(tempNode.firstChild){
						tempNode.removeChild(tempNode.firstChild);
					}
				}

				if(i == length - 1){
					domCtr.place(node, rNode, position);
				}else{
					rNode.parentNode.insertBefore(node, rNode);
				}
				rNode = node;
			}
		},

		/*=====
		position: function(){
			
			
			
			

			return dojo.map(this, dojo.position); 
		},

		attr: function(property, value){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			//	|	
			
			return; 
			return; 
		},

		style: function(property, value){
			
			
			
			
			
			
			
			
			
			
			return; 
			return; 
		},

		addClass: function(className){
			
			
			
			
			
			return; 
		},

		removeClass: function(className){
			
			
			
			
			
			
			
			
			return; 
		},

		toggleClass: function(className, condition){
			
			
			
			
			
			
			
			return; 
		},

		empty: function(){
			
			
			
			
			return this.forEach("item.innerHTML='';"); 
			
		},
		=====*/

		
		attr: awc(getSet(domAttr), magicGuard),
		style: awc(getSet(domStyle), magicGuard),

		addClass: aafe(domCls.add),
		removeClass: aafe(domCls.remove),
		replaceClass: aafe(domCls.replace),
		toggleClass: aafe(domCls.toggle),

		empty: aafe(domCtr.empty),
		removeAttr: aafe(domAttr.remove),

		position: aam(domGeom.position),
		marginBox: aam(domGeom.getMarginBox),

		

		/*
		destroy: function(){
			
			
			this.forEach(d.destroy);
			
		},
		*/

		place: function(/*String||Node*/ queryOrNode, /*String*/ position){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			var item = query(queryOrNode)[0];
			return this.forEach(function(node){ domCtr.place(node, item, position); }); 
		},

		orphan: function(/*String?*/ filter){
			
			
			
			
			
			
			
			return (filter ? query._filterResult(this, filter) : this).forEach(orphan); 
		},

		adopt: function(/*String||Array||DomNode*/ queryOrListOrNode, /*String?*/ position){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			return query(queryOrListOrNode).place(this[0], position)._stash(this);	
		},

		
		query: function(/*String*/ queryStr){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

			
			if(!queryStr){ return this; }
			var ret = new NodeList;
			this.map(function(node){
				
				query(queryStr, node).forEach(function(subNode){
					if(subNode !== undefined){
						ret.push(subNode);
					}
				});
			});
			return ret._stash(this);	
		},

		filter: function(/*String|Function*/ filter){
			
			
			
			
			
			
			
			
			
			
			//		|		
			
			
			
			
			

			var a = arguments, items = this, start = 0;
			if(typeof filter == "string"){ 
				items = query._filterResult(this, a[0]);
				if(a.length == 1){
					
					return items._stash(this); 
				}
				
				start = 1;
			}
			return this._wrap(array.filter(items, a[start], a[start + 1]), this);	
		},

		/*
		
		clone: function(){
			
			
			
		},
		*/

		addContent: function(/*String||DomNode||Object||dojo.NodeList*/ content, /*String||Integer?*/ position){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			content = this._normalize(content, this[0]);
			for(var i = 0, node; (node = this[i]); i++){
				this._place(content, node, position, i > 0);
			}
			return this; 
		}
	});

	/*===== return dojo.NodeList; =====*/
	return NodeList;
});

},
'dojo/query':function(){
define(["./_base/kernel", "./has", "./dom", "./on", "./_base/array", "./_base/lang", "./selector/_loader", "./selector/_loader!default"],
	function(dojo, has, dom, on, array, lang, loader, defaultEngine){
"use strict";

	has.add("array-extensible", function(){
		
		return lang.delegate([], {length: 1}).length == 1 && !has("bug-for-in-skips-shadowed");
	});
	
	var ap = Array.prototype, aps = ap.slice, apc = ap.concat, forEach = array.forEach;

	var tnl = function(/*Array*/ a, /*dojo.NodeList?*/ parent, /*Function?*/ NodeListCtor){
		
		
		
		
		
		
		
		
		
		
		
		
		var nodeList = new (NodeListCtor || this._NodeListCtor || nl)(a);
		return parent ? nodeList._stash(parent) : nodeList;
	};

	var loopBody = function(f, a, o){
		a = [0].concat(aps.call(a, 0));
		o = o || dojo.global;
		return function(node){
			a[0] = node;
			return f.apply(o, a);
		};
	};

	

	var adaptAsForEach = function(f, o){
		
		
		
		
		
		
		
		
		return function(){
			this.forEach(loopBody(f, arguments, o));
			return this;	
		};
	};

	var adaptAsMap = function(f, o){
		
		
		
		
		
		
		
		return function(){
			return this.map(loopBody(f, arguments, o));
		};
	};

	var adaptAsFilter = function(f, o){
		
		
		
		
		
		
		return function(){
			return this.filter(loopBody(f, arguments, o));
		};
	};

	var adaptWithCondition = function(f, g, o){
		
		
		
		
		
		
		
		
		
		return function(){
			var a = arguments, body = loopBody(f, a, o);
			if(g.call(o || dojo.global, a)){
				return this.map(body);	
			}
			this.forEach(body);
			return this;	
		};
	};

	var NodeList = function(array){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//		|	
		//		|	
		//		|	
		
		
		
		//		|	
		
		//		|	
		
		
		//		|	
		//		|	
		//		|	var node = l[3]; 
		//		|	var newList = l.at(1, 3); 
		
		
		//		|	
		
		//		|	
		
		//		|	
		
		//		|	
		
		
		
		
		//		|	
		//		|	
		
		//		|	
		
		
		
		
		//		|	
		
		
		//		|	
		
		
		
		
		
		
		
		
		
		//		|		.at(1, 3, 8) 
		
		
		var isNew = this instanceof nl && has("array-extensible");
		if(typeof array == "number"){
			array = Array(array);
		}
		var nodeArray = (array && "length" in array) ? array : arguments;
		if(isNew || !nodeArray.sort){
			
			var target = isNew ? this : [],
				l = target.length = nodeArray.length;
			for(var i = 0; i < l; i++){
				target[i] = nodeArray[i];
			}
			if(isNew){
				
				
				
				return target;
			}
			nodeArray = target;
		}
		
		
		lang._mixin(nodeArray, nlp);
		nodeArray._NodeListCtor = function(array){
			
			return nl(array);
		};
		return nodeArray;
	};
	
	var nl = NodeList, nlp = nl.prototype = 
		has("array-extensible") ? [] : {};

	

	nl._wrap = nlp._wrap = tnl;
	nl._adaptAsMap = adaptAsMap;
	nl._adaptAsForEach = adaptAsForEach;
	nl._adaptAsFilter  = adaptAsFilter;
	nl._adaptWithCondition = adaptWithCondition;

	

	
	forEach(["slice", "splice"], function(name){
		var f = ap[name];
		
		
		
		
		nlp[name] = function(){ return this._wrap(f.apply(this, arguments), name == "slice" ? this : null); };
	});
	

	
	forEach(["indexOf", "lastIndexOf", "every", "some"], function(name){
		var f = array[name];
		nlp[name] = function(){ return f.apply(dojo, [this].concat(aps.call(arguments, 0))); };
	});

	/*===== var NodeList = dojo.NodeList; =====*/
	lang.extend(NodeList, {
		
		constructor: nl,
		_NodeListCtor: nl,
		toString: function(){
			
			return this.join(",");
		},
		_stash: function(parent){
			
			
			
			
			
			
			
			
			
			
			
			
			//	|	
			
			
			
			
			//	|		
			
			
			
			this._parent = parent;
			return this; 
		},

		on: function(eventName, listener){
			
			
			
			
			
			
			
			
			
			var handles = this.map(function(node){
				return on(node, eventName, listener); 
			});
			handles.remove = function(){
				for(var i = 0; i < handles.length; i++){
					handles[i].remove();
				}
			};
			return handles;
		},

		end: function(){
			
			
			
			
			
			
			
			
			
			//	|			
			
			
			//	|		
			
			
			if(this._parent){
				return this._parent;
			}else{
				
				return new this._NodeListCtor(0);
			}
		},

		// http:

		
		//		http:

		
		
		
		
		

		/*=====
		slice: function(begin, end){
			
			
			
			
			
			
			//		documentation)[http:
			
			
			
			
			
			
			
			
			
			return this._wrap(a.slice.apply(this, arguments));
		},

		splice: function(index, howmany, item){
			
			
			
			
			
			
			
			
			//		documentation)[http:
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			return this._wrap(a.splice.apply(this, arguments));
		},

		indexOf: function(value, fromIndex){
			
			
			
			
			
			
			
			
			
			
			//		docs)[http:
			
			
			return d.indexOf(this, value, fromIndex); 
		},

		lastIndexOf: function(value, fromIndex){
			
			
			
			
			
			
			//		docs)[http:
			
			
			
			
			
			
			return d.lastIndexOf(this, value, fromIndex); 
		},

		every: function(callback, thisObject){
			
			
			//		docs)[http:
			
			
			
			
			
			return d.every(this, callback, thisObject); 
		},

		some: function(callback, thisObject){
			
			
			
			
			
			//		documentation)[http:
			
			
			return d.some(this, callback, thisObject); 
		},
		=====*/

		concat: function(item){
			
			
			
			
			
			
			
			//		docs)[http:
			
			
			
			
			

			
			

			
			
			
			
			
			
			
			

			var t = lang.isArray(this) ? this : aps.call(this, 0),
				m = array.map(arguments, function(a){
					return a && !lang.isArray(a) &&
						(typeof NodeList != "undefined" && a.constructor === NodeList || a.constructor === this._NodeListCtor) ?
							aps.call(a, 0) : a;
				});
			return this._wrap(apc.apply(t, m), this);	
		},

		map: function(/*Function*/ func, /*Function?*/ obj){
			
			
			
			
			///return d.map(this, func, obj, d.NodeList); 
			return this._wrap(array.map(this, func, obj), this); 
		},

		forEach: function(callback, thisObj){
			
			
			
			
			forEach(this, callback, thisObj);
			
			return this; 
		},
		filter: function(/*String|Function*/ filter){
			
			
			
			
			
			
			
			
			
			
			//		|		
			
			
			
			
			

			var a = arguments, items = this, start = 0;
			if(typeof filter == "string"){ 
				items = query._filterResult(this, a[0]);
				if(a.length == 1){
					
					return items._stash(this); 
				}
				
				start = 1;
			}
			return this._wrap(array.filter(items, a[start], a[start + 1]), this);	
		},
		instantiate: function(/*String|Object*/ declaredClass, /*Object?*/ properties){
			
			
			
			
			
			
			
			var c = lang.isFunction(declaredClass) ? declaredClass : lang.getObject(declaredClass);
			properties = properties || {};
			return this.forEach(function(node){
				new c(properties, node);
			});	
		},
		at: function(/*===== index =====*/){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			//	|		console.log(n); 
			
			
			
			
			var t = new this._NodeListCtor(0);
			forEach(arguments, function(i){
				if(i < 0){ i = this.length + i; }
				if(this[i]){ t.push(this[i]); }
			}, this);
			return t._stash(this); 
		}
	});


/*===== 
dojo.query = function(selector, context){
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//	|		dojo.stopEvent(e); 
	
	
	
	
	//	|				
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	return new dojo.NodeList(); 
};
=====*/

function queryForEngine(engine, NodeList){
	var query = function(/*String*/ query, /*String|DOMNode?*/ root){
		
		
		
		
		if(typeof root == "string"){
			root = dom.byId(root);
			if(!root){
				return new NodeList([]);
			}
		}
		var results = typeof query == "string" ? engine(query, root) : query.orphan ? query : [query];
		if(results.orphan){
			
			return results; 
		}
		return new NodeList(results);
	};
	query.matches = engine.match || function(node, selector, root){
		
		
		return query.filter([node], selector, root).length > 0;
	};
	
	query.filter = engine.filter || function(nodes, selector, root){
		
		
		return query(selector, root).filter(function(node){
			return array.indexOf(nodes, node) > -1;
		});
	};
	if(typeof engine != "function"){
		var search = engine.search;
		engine = function(selector, root){
			
			return search(root || document, selector);
		};
	}
	return query;
}
var query = queryForEngine(defaultEngine, NodeList);





dojo.query = queryForEngine(defaultEngine, function(array){
	
	return NodeList(array);
});

query.load = /*===== dojo.query.load= ======*/ function(id, parentRequire, loaded, config){
	
	
	
	//	|		
	
	
	loader.load(id, parentRequire, function(engine){
		loaded(queryForEngine(engine, NodeList));
	});
};

dojo._filterQueryResult = query._filterResult = function(nodes, selector, root){
	return new NodeList(query.filter(nodes, selector, root));
};
dojo.NodeList = query.NodeList = NodeList;
return query;
});

},
'dojo/has':function(){
define(["require"], function(require) {
	
	
	
	
	
	
	
	
	
	
	
	//		This module adopted from https:

	
	
	var has = require.has || function(){};
	if(!1){
		
		
		
		var
			isBrowser =
				
				typeof window != "undefined" &&
				typeof location != "undefined" &&
				typeof document != "undefined" &&
				window.location == location && window.document == document,

			
			global = this,
			doc = isBrowser && document,
			element = doc && doc.createElement("DiV"),
			cache = {};

		has = /*===== dojo.has= =====*/ function(name){
			
			
			
			
			
			
			
			
			

			return cache[name] = typeof cache[name] == "function" ? cache[name](global, doc, element) : cache[name]; 
		};

		has.cache = cache;

		has.add = /*====== dojo.has.add= ======*/ function(name, test, now, force){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			//	|						
			//	|						
			//	|						
			//	|						return false; 
			

			(typeof cache[name]=="undefined" || force) && (cache[name]= test);
			return now && has(name);
		};

		
		
		
		true || has.add("host-browser", isBrowser);
		true || has.add("dom", isBrowser);
		true || has.add("dojo-dom-ready-api", 1);
		true || has.add("dojo-sniff", 1);
	}

	if(1){
		var agent = navigator.userAgent;
		
		has.add("dom-addeventlistener", !!document.addEventListener);
		has.add("touch", "ontouchstart" in document);
		
		has.add("device-width", screen.availWidth || innerWidth);
		has.add("agent-ios", !!agent.match(/iPhone|iP[ao]d/));
		has.add("agent-android", agent.indexOf("android") > 1);
	}

	has.clearElement = /*===== dojo.has.clearElement= ======*/ function(element) {
		
		
		element.innerHTML= "";
		return element;
	};

	has.normalize = /*===== dojo.has.normalize= ======*/ function(id, toAbsMid){
		
		
		
		
		
		var
			tokens = id.match(/[\?:]|[^:\?]*/g), i = 0,
			get = function(skip){
				var term = tokens[i++];
				if(term == ":"){
					
					return 0;
				}else{
					
					if(tokens[i++] == "?"){
						if(!skip && has(term)){
							
							return get();
						}else{
							
							get(true);
							return get(skip);
						}
					}
					
					return term || 0;
				}
			};
		id = get();
		return id && toAbsMid(id);
	};

	has.load = /*===== dojo.has.load= ======*/ function(id, parentRequire, loaded){
		
		
		
		
		
		
		
		
		
		
		
		

		if(id){
			parentRequire([id], loaded);
		}else{
			loaded();
		}
	};

	return has;
});

},
'dojo/_base/loader':function(){
define(["./kernel", "../has", "require", "module", "./json", "./lang", "./array"], function(dojo, has, require, thisModule, json, lang, array) {
	
	
	
	

	
	

	if (!1){
		console.error("cannot load the Dojo v1.x loader with a foreign loader");
		return 0;
	}

	var makeErrorToken = function(id){
			return {src:thisModule.id, id:id};
		},

		slashName = function(name){
			return name.replace(/\./g, "/");
		},

		buildDetectRe = /\/\/>>built/,

		dojoRequireCallbacks = [],
		dojoRequireModuleStack = [],

		dojoRequirePlugin = function(mid, require, loaded){
			dojoRequireCallbacks.push(loaded);
			array.forEach(mid.split(","), function(mid){
				var module = getModule(mid, require.module);
				dojoRequireModuleStack.push(module);
				injectModule(module);
			});
			checkDojoRequirePlugin();
		},

		checkDojoRequirePlugin = function(){
			dojoRequireModuleStack = array.filter(dojoRequireModuleStack, function(module){
				return module.injected!==arrived && !module.executed;
			});
			if(!dojoRequireModuleStack.length){
				loaderVars.holdIdle();
				var oldCallbacks = dojoRequireCallbacks;
				dojoRequireCallbacks = [];
				array.forEach(oldCallbacks, function(cb){cb(1);});
				loaderVars.releaseIdle();
			}
		},

		dojoLoadInitPlugin = function(mid, require, loaded){
			
			
			
			
			
			
			
			
			
			// 
			
			
			
			
			
			
			
			//         
			//         
			//         
			
			//         
			
			
			
			
			
			
			//     
			//     
			//     
			//     
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			require([mid], function(bundle){
				
				require(bundle.names, function(){
					
					for(var scopeText = "", args= [], i = 0; i<arguments.length; i++){
						scopeText+= "var " + bundle.names[i] + "= arguments[" + i + "]; ";
						args.push(arguments[i]);
					}
					eval(scopeText);

					var callingModule = require.module,
						deps = [],
						hold = {},
						requireList = [],
						p,
						syncLoaderApi = {
							provide:function(moduleName){
								
								moduleName = slashName(moduleName);
								var providedModule = getModule(moduleName, callingModule);
								if(providedModule!==callingModule){
									setArrived(providedModule);
								}
							},
							require:function(moduleName, omitModuleCheck){
								moduleName = slashName(moduleName);
								omitModuleCheck && (getModule(moduleName, callingModule).result = nonmodule);
								requireList.push(moduleName);
							},
							requireLocalization:function(moduleName, bundleName, locale){
								
								deps.length || (deps = ["dojo/i18n"]);

								
								locale = (locale || dojo.locale).toLowerCase();
								moduleName = slashName(moduleName) + "/nls/" + (/root/i.test(locale) ? "" : locale + "/") + slashName(bundleName);
								if(getModule(moduleName, callingModule).isXd){
									deps.push("dojo/i18n!" + moduleName);
								}
							},
							loadInit:function(f){
								f();
							}
						};

					
					try{
						for(p in syncLoaderApi){
							hold[p] = dojo[p];
							dojo[p] = syncLoaderApi[p];
						}
						bundle.def.apply(null, args);
					}catch(e){
						signal("error", [makeErrorToken("failedDojoLoadInit"), e]);
					}finally{
						for(p in syncLoaderApi){
							dojo[p] = hold[p];
						}
					}

					
					requireList.length && deps.push("dojo/require!" + requireList.join(","));

					dojoRequireCallbacks.push(loaded);
					array.forEach(requireList, function(mid){
						var module = getModule(mid, require.module);
						dojoRequireModuleStack.push(module);
						injectModule(module);
					});
					checkDojoRequirePlugin();
				});
			});
		},

		extractApplication = function(
			text,             
			startSearch,      
			startApplication  
		){
			
			
			var parenRe = /\(|\)/g,
				matchCount = 1,
				match;
			parenRe.lastIndex = startSearch;
			while((match = parenRe.exec(text))){
				if(match[0] == ")"){
					matchCount -= 1;
				}else{
					matchCount += 1;
				}
				if(matchCount == 0){
					break;
				}
			}

			if(matchCount != 0){
				throw "unmatched paren around character " + parenRe.lastIndex + " in: " + text;
			}

			
			return [dojo.trim(text.substring(startApplication, parenRe.lastIndex))+";\n", parenRe.lastIndex];
		},

		
		
		
		
		
		
		
		
		
		
		
		
		removeCommentRe = /(\/\*([\s\S]*?)\*\/|\/\/(.*)$)/mg,

		syncLoaderApiRe = /(^|\s)dojo\.(loadInit|require|provide|requireLocalization|requireIf|requireAfterIf|platformRequire)\s*\(/mg,

		amdLoaderApiRe = /(^|\s)(require|define)\s*\(/m,

		extractLegacyApiApplications = function(text, noCommentText){
			
			
			
			
			
			//   dojo.loadInit(
			
			
			
			//   \n 0 && dojo.loadInit(
			
			
			
			
			
			

			var match, startSearch, startApplication, application,
				loadInitApplications = [],
				otherApplications = [],
				allApplications = [];

			
			noCommentText = noCommentText || text.replace(removeCommentRe, function(match){
				
				
				syncLoaderApiRe.lastIndex = amdLoaderApiRe.lastIndex = 0;
				return (syncLoaderApiRe.test(match) || amdLoaderApiRe.test(match)) ? "" : match;
			});

			
			while((match = syncLoaderApiRe.exec(noCommentText))){
				startSearch = syncLoaderApiRe.lastIndex;
				startApplication = startSearch  - match[0].length;
				application = extractApplication(noCommentText, startSearch, startApplication);
				if(match[2]=="loadInit"){
					loadInitApplications.push(application[0]);
				}else{
					otherApplications.push(application[0]);
				}
				syncLoaderApiRe.lastIndex = application[1];
			}
			allApplications = loadInitApplications.concat(otherApplications);
			if(allApplications.length || !amdLoaderApiRe.test(noCommentText)){
				
				return [text.replace(/(^|\s)dojo\.loadInit\s*\(/g, "\n0 && dojo.loadInit("), allApplications.join(""), allApplications];
			}else{
				
				return 0;
			}
		},

		transformToAmd = function(module, text){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

			var extractResult, id, names = [], namesAsStrings = [];
			if(buildDetectRe.test(text) || !(extractResult = extractLegacyApiApplications(text))){
				
				
				return 0;
			}

			
			id = module.mid + "-*loadInit";

			
			

			for(var p in getModule("dojo", module).result.scopeMap){
				names.push(p);
				namesAsStrings.push('"' + p + '"');
			}

			
			return "
				"define('" + id + "',{\n" +
				"\tnames:" + dojo.toJson(names) + ",\n" +
				"\tdef:function(" + names.join(",") + "){" + extractResult[1] + "}" +
				"});\n\n" +
			    "define(" + dojo.toJson(names.concat(["dojo/loadInit!"+id])) + ", function(" + names.join(",") + "){\n" + extractResult[0] + "});";
		},

		loaderVars = require.initSyncLoader(dojoRequirePlugin, checkDojoRequirePlugin, transformToAmd),

		sync =
			loaderVars.sync,

		xd =
			loaderVars.xd,

		arrived =
			loaderVars.arrived,

		nonmodule =
			loaderVars.nonmodule,

		executing =
			loaderVars.executing,

		executed =
			loaderVars.executed,

		syncExecStack =
			loaderVars.syncExecStack,

		modules =
			loaderVars.modules,

		execQ =
			loaderVars.execQ,

		getModule =
			loaderVars.getModule,

		injectModule =
			loaderVars.injectModule,

		setArrived =
			loaderVars.setArrived,

		signal =
			loaderVars.signal,

		finishExec =
			loaderVars.finishExec,

		execModule =
			loaderVars.execModule,

		getLegacyMode =
			loaderVars.getLegacyMode;

	dojo.provide = function(mid){
		var executingModule = syncExecStack[0],
			module = lang.mixin(getModule(slashName(mid), require.module), {
				executed:executing,
				result:lang.getObject(mid, true)
			});
		setArrived(module);
		if(executingModule){
			(executingModule.provides || (executingModule.provides = [])).push(function(){
				module.result = lang.getObject(mid);
				delete module.provides;
				module.executed!==executed && finishExec(module);
			});
		}
		return module.result;
	};

	has.add("config-publishRequireResult", 1, 0, 0);

	dojo.require = function(moduleName, omitModuleCheck) {
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	   	|		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		function doRequire(mid, omitModuleCheck){
			var module = getModule(slashName(mid), require.module);
			if(syncExecStack.length && syncExecStack[0].finish){
				
				
				syncExecStack[0].finish.push(mid);
				return undefined;
			}

			
			if(module.executed){
				return module.result;
			}
			omitModuleCheck && (module.result = nonmodule);

			var currentMode = getLegacyMode();

			
			
			
			injectModule(module);
			
			currentMode = getLegacyMode();

			
			if(module.executed!==executed && module.injected===arrived){
				
				
				loaderVars.holdIdle();
				execModule(module);
				loaderVars.releaseIdle();
			}
			if(module.executed){
				return module.result;
			}

			if(currentMode==sync){
				
				
				
				if(module.cjs){
					
					execQ.unshift(module);
				}else{
					
					syncExecStack.length && (syncExecStack[0].finish= [mid]);
				}
			}else{
				
				
				execQ.push(module);
			}
			return undefined;
		}

		var result = doRequire(moduleName, omitModuleCheck);
		if(has("config-publishRequireResult") && !lang.exists(moduleName) && result!==undefined){
			lang.setObject(moduleName, result);
		}
		return result;
	};

	dojo.loadInit = function(f) {
		f();
	};

	dojo.registerModulePath = function(/*String*/moduleName, /*String*/prefix){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		var paths = {};
		paths[moduleName.replace(/\./g, "/")] = prefix;
		require({paths:paths});
	};

	dojo.platformRequire = function(/*Object*/modMap){
		
		
		
		
		
		
		
		
		
		
		
		
		
 		
		
		//		|			"foo.sample", 
		
		//		|			["foo.bar.baz", true] 
		
		
		
		

		var result = (modMap.common || []).concat(modMap[dojo._name] || modMap["default"] || []),
			temp;
		while(result.length){
			if(lang.isArray(temp = result.shift())){
				dojo.require.apply(dojo, temp);
			}else{
				dojo.require(temp);
			}
		}
	};

	dojo.requireIf = dojo.requireAfterIf = function(/*Boolean*/ condition, /*String*/ moduleName, /*Boolean?*/omitModuleCheck){
		
		
		
		
		
		

		if(condition){
			dojo.require(moduleName, omitModuleCheck);
		}
	};

	dojo.requireLocalization = function(/*String*/moduleName, /*String*/bundleName, /*String?*/locale){
		require(["../i18n"], function(i18n){
			i18n.getLocalization(moduleName, bundleName, locale);
		});
	};

	return {
		extractLegacyApiApplications:extractLegacyApiApplications,
		require:loaderVars.dojoRequirePlugin,
		loadInit:dojoLoadInitPlugin
	};
});

},
'dojo/json':function(){
define(["./has"], function(has){
	"use strict";
	var hasJSON = typeof JSON != "undefined";
	has.add("json-parse", hasJSON); 
		// Firefox 3.5/Gecko 1.9 fails to use replacer in stringify properly https:
	has.add("json-stringify", hasJSON && JSON.stringify({a:0}, function(k,v){return v||1;}) == '{"a":1}'); 
	if(has("json-stringify")){
		return JSON;
	}
	else{
		var escapeString = function(/*String*/str){
			
			
			
			
			return ('"' + str.replace(/(["\\])/g, '\\$1') + '"').
				replace(/[\f]/g, "\\f").replace(/[\b]/g, "\\b").replace(/[\n]/g, "\\n").
				replace(/[\t]/g, "\\t").replace(/[\r]/g, "\\r"); 
		};
		return {
			parse: has("json-parse") ? JSON.parse : function(str, strict){
				
				// 		Parses a [JSON](http:
				
				//		This function follows [native JSON API](https:
				
				
				
				
				
				
				
				
				
				
				
				
				
				if(strict && !/^([\s\[\{]*(?:"(?:\\.|[^"])+"|-?\d[\d\.]*(?:[Ee][+-]?\d+)?|null|true|false|)[\s\]\}]*(?:,|:|$))+$/.test(str)){
					throw new SyntaxError("Invalid characters in JSON");
				}
				return eval('(' + str + ')');
			},
			stringify: function(value, replacer, spacer){
				
				//		Returns a [JSON](http:
				
				//		Returns a [JSON](http:
				//		This function follows [native JSON API](https:
				
				
				
				
				
				
				
				
				
				
				
				
				
				var undef;
				if(typeof replacer == "string"){
					spacer = replacer;
					replacer = null;
				}
				function stringify(it, indent, key){
					if(replacer){
						it = replacer(key, it);
					}
					var val, objtype = typeof it;
					if(objtype == "number"){
						return isFinite(it) ? it + "" : "null";
					}
					if(objtype == "boolean"){
						return it + "";
					}
					if(it === null){
						return "null";
					}
					if(typeof it == "string"){
						return escapeString(it);
					}
					if(objtype == "function" || objtype == "undefined"){
						return undef; 
					}
					
					
					if(typeof it.toJSON == "function"){
						return stringify(it.toJSON(key), indent, key);
					}
					if(it instanceof Date){
						return '"{FullYear}-{Month+}-{Date}T{Hours}:{Minutes}:{Seconds}Z"'.replace(/\{(\w+)(\+)?\}/g, function(t, prop, plus){
							var num = it["getUTC" + prop]() + (plus ? 1 : 0);
							return num < 10 ? "0" + num : num;
						});
					}
					if(it.valueOf() !== it){
						
						return stringify(it.valueOf(), indent, key);
					}
					var nextIndent= spacer ? (indent + spacer) : "";
					/* we used to test for DOM nodes and throw, but FF serializes them as {}, so cross-browser consistency is probably not efficiently attainable */ 
				
					var sep = spacer ? " " : "";
					var newLine = spacer ? "\n" : "";
				
					
					if(it instanceof Array){
						var itl = it.length, res = [];
						for(key = 0; key < itl; key++){
							var obj = it[key];
							val = stringify(obj, nextIndent, key);
							if(typeof val != "string"){
								val = "null";
							}
							res.push(newLine + nextIndent + val);
						}
						return "[" + res.join(",") + newLine + indent + "]";
					}
					
					var output = [];
					for(key in it){
						var keyStr;
						if(typeof key == "number"){
							keyStr = '"' + key + '"';
						}else if(typeof key == "string"){
							keyStr = escapeString(key);
						}else{
							
							continue;
						}
						val = stringify(it[key], nextIndent, key);
						if(typeof val != "string"){
							
							continue;
						}
						
						
						output.push(newLine + nextIndent + keyStr + ":" + sep + val);
					}
					return "{" + output.join(",") + newLine + indent + "}"; 
				}
				return stringify(value, "", "");
			}
		};
	}
});

},
'dojo/_base/declare':function(){
define(["./kernel", "../has", "./lang"], function(dojo, has, lang){
	
	
	
	

	var mix = lang.mixin, op = Object.prototype, opts = op.toString,
		xtor = new Function, counter = 0, cname = "constructor";

	function err(msg, cls){ throw new Error("declare" + (cls ? " " + cls : "") + ": " + msg); }

	// C3 Method Resolution Order (see http:
	function c3mro(bases, className){
		var result = [], roots = [{cls: 0, refs: []}], nameMap = {}, clsCount = 1,
			l = bases.length, i = 0, j, lin, base, top, proto, rec, name, refs;

		
		for(; i < l; ++i){
			base = bases[i];
			if(!base){
				err("mixin #" + i + " is unknown. Did you use dojo.require to pull it in?", className);
			}else if(opts.call(base) != "[object Function]"){
				err("mixin #" + i + " is not a callable constructor.", className);
			}
			lin = base._meta ? base._meta.bases : [base];
			top = 0;
			
			for(j = lin.length - 1; j >= 0; --j){
				proto = lin[j].prototype;
				if(!proto.hasOwnProperty("declaredClass")){
					proto.declaredClass = "uniqName_" + (counter++);
				}
				name = proto.declaredClass;
				if(!nameMap.hasOwnProperty(name)){
					nameMap[name] = {count: 0, refs: [], cls: lin[j]};
					++clsCount;
				}
				rec = nameMap[name];
				if(top && top !== rec){
					rec.refs.push(top);
					++top.count;
				}
				top = rec;
			}
			++top.count;
			roots[0].refs.push(top);
		}

		
		while(roots.length){
			top = roots.pop();
			result.push(top.cls);
			--clsCount;
			
			while(refs = top.refs, refs.length == 1){
				top = refs[0];
				if(!top || --top.count){
					
					top = 0;
					break;
				}
				result.push(top.cls);
				--clsCount;
			}
			if(top){
				
				for(i = 0, l = refs.length; i < l; ++i){
					top = refs[i];
					if(!--top.count){
						roots.push(top);
					}
				}
			}
		}
		if(clsCount){
			err("can't build consistent linearization", className);
		}

		
		base = bases[0];
		result[0] = base ?
			base._meta && base === result[result.length - base._meta.bases.length] ?
				base._meta.bases.length : 1 : 0;

		return result;
	}

	function inherited(args, a, f){
		var name, chains, bases, caller, meta, base, proto, opf, pos,
			cache = this._inherited = this._inherited || {};

		
		if(typeof args == "string"){
			name = args;
			args = a;
			a = f;
		}
		f = 0;

		caller = args.callee;
		name = name || caller.nom;
		if(!name){
			err("can't deduce a name to call inherited()", this.declaredClass);
		}

		meta = this.constructor._meta;
		bases = meta.bases;

		pos = cache.p;
		if(name != cname){
			
			if(cache.c !== caller){
				
				pos = 0;
				base = bases[0];
				meta = base._meta;
				if(meta.hidden[name] !== caller){
					
					chains = meta.chains;
					if(chains && typeof chains[name] == "string"){
						err("calling chained method with inherited: " + name, this.declaredClass);
					}
					
					do{
						meta = base._meta;
						proto = base.prototype;
						if(meta && (proto[name] === caller && proto.hasOwnProperty(name) || meta.hidden[name] === caller)){
							break;
						}
					}while(base = bases[++pos]); 
					pos = base ? pos : -1;
				}
			}
			
			base = bases[++pos];
			if(base){
				proto = base.prototype;
				if(base._meta && proto.hasOwnProperty(name)){
					f = proto[name];
				}else{
					opf = op[name];
					do{
						proto = base.prototype;
						f = proto[name];
						if(f && (base._meta ? proto.hasOwnProperty(name) : f !== opf)){
							break;
						}
					}while(base = bases[++pos]); 
				}
			}
			f = base && f || op[name];
		}else{
			
			if(cache.c !== caller){
				
				pos = 0;
				meta = bases[0]._meta;
				if(meta && meta.ctor !== caller){
					
					chains = meta.chains;
					if(!chains || chains.constructor !== "manual"){
						err("calling chained constructor with inherited", this.declaredClass);
					}
					
					while(base = bases[++pos]){ 
						meta = base._meta;
						if(meta && meta.ctor === caller){
							break;
						}
					}
					pos = base ? pos : -1;
				}
			}
			
			while(base = bases[++pos]){	
				meta = base._meta;
				f = meta ? meta.ctor : base;
				if(f){
					break;
				}
			}
			f = base && f;
		}

		
		cache.c = f;
		cache.p = pos;

		
		if(f){
			return a === true ? f : f.apply(this, a || args);
		}
		
	}

	function getInherited(name, args){
		if(typeof name == "string"){
			return this.__inherited(name, args, true);
		}
		return this.__inherited(name, true);
	}

	function inherited__debug(args, a1, a2){
		var f = this.getInherited(args, a1);
		if(f){ return f.apply(this, a2 || a1 || args); }
		
	}

	var inheritedImpl = dojo.config.isDebug ? inherited__debug : inherited;

	
	function isInstanceOf(cls){
		var bases = this.constructor._meta.bases;
		for(var i = 0, l = bases.length; i < l; ++i){
			if(bases[i] === cls){
				return true;
			}
		}
		return this instanceof cls;
	}

	function mixOwn(target, source){
		
		for(var name in source){
			if(name != cname && source.hasOwnProperty(name)){
				target[name] = source[name];
			}
		}
		if(has("bug-for-in-skips-shadowed")){
			for(var extraNames= lang._extraNames, i= extraNames.length; i;){
				name = extraNames[--i];
				if(name != cname && source.hasOwnProperty(name)){
					  target[name] = source[name];
				}
			}
		}
	}

	
	function safeMixin(target, source){
		var name, t;
		
		for(name in source){
			t = source[name];
			if((t !== op[name] || !(name in op)) && name != cname){
				if(opts.call(t) == "[object Function]"){
					
					t.nom = name;
				}
				target[name] = t;
			}
		}
		if(has("bug-for-in-skips-shadowed")){
			for(var extraNames= lang._extraNames, i= extraNames.length; i;){
				name = extraNames[--i];
				t = source[name];
				if((t !== op[name] || !(name in op)) && name != cname){
					if(opts.call(t) == "[object Function]"){
						
						  t.nom = name;
					}
					target[name] = t;
				}
			}
		}
		return target;
	}

	function extend(source){
		declare.safeMixin(this.prototype, source);
		return this;
	}

	
	function chainedConstructor(bases, ctorSpecial){
		return function(){
			var a = arguments, args = a, a0 = a[0], f, i, m,
				l = bases.length, preArgs;

			if(!(this instanceof a.callee)){
				
				return applyNew(a);
			}

			
			
			
			if(ctorSpecial && (a0 && a0.preamble || this.preamble)){
				
				preArgs = new Array(bases.length);
				
				preArgs[0] = a;
				for(i = 0;;){
					
					a0 = a[0];
					if(a0){
						f = a0.preamble;
						if(f){
							a = f.apply(this, a) || a;
						}
					}
					
					f = bases[i].prototype;
					f = f.hasOwnProperty("preamble") && f.preamble;
					if(f){
						a = f.apply(this, a) || a;
					}
					
					
					
					
					
					if(++i == l){
						break;
					}
					preArgs[i] = a;
				}
			}
			
			for(i = l - 1; i >= 0; --i){
				f = bases[i];
				m = f._meta;
				f = m ? m.ctor : f;
				if(f){
					f.apply(this, preArgs ? preArgs[i] : a);
				}
			}
			
			f = this.postscript;
			if(f){
				f.apply(this, args);
			}
		};
	}


	
	function singleConstructor(ctor, ctorSpecial){
		return function(){
			var a = arguments, t = a, a0 = a[0], f;

			if(!(this instanceof a.callee)){
				
				return applyNew(a);
			}

			
			
			
			if(ctorSpecial){
				
				if(a0){
					
					f = a0.preamble;
					if(f){
						t = f.apply(this, t) || t;
					}
				}
				f = this.preamble;
				if(f){
					
					f.apply(this, t);
					
					
					
					
					
				}
			}
			
			if(ctor){
				ctor.apply(this, a);
			}
			
			f = this.postscript;
			if(f){
				f.apply(this, a);
			}
		};
	}

	
	function simpleConstructor(bases){
		return function(){
			var a = arguments, i = 0, f, m;

			if(!(this instanceof a.callee)){
				
				return applyNew(a);
			}

			
			
			
			
			for(; f = bases[i]; ++i){ 
				m = f._meta;
				f = m ? m.ctor : f;
				if(f){
					f.apply(this, a);
					break;
				}
			}
			
			f = this.postscript;
			if(f){
				f.apply(this, a);
			}
		};
	}

	function chain(name, bases, reversed){
		return function(){
			var b, m, f, i = 0, step = 1;
			if(reversed){
				i = bases.length - 1;
				step = -1;
			}
			for(; b = bases[i]; i += step){ 
				m = b._meta;
				f = (m ? m.hidden : b.prototype)[name];
				if(f){
					f.apply(this, arguments);
				}
			}
		};
	}

	
	
	
	function forceNew(ctor){
		
		
		xtor.prototype = ctor.prototype;
		var t = new xtor;
		xtor.prototype = null;	
		return t;
	}

	
	
	
	function applyNew(args){
		
		
		var ctor = args.callee, t = forceNew(ctor);
		
		ctor.apply(t, args);
		return t;
	}

	function declare(className, superclass, props){
		
		if(typeof className != "string"){
			props = superclass;
			superclass = className;
			className = "";
		}
		props = props || {};

		var proto, i, t, ctor, name, bases, chains, mixins = 1, parents = superclass;

		
		if(opts.call(superclass) == "[object Array]"){
			
			bases = c3mro(superclass, className);
			t = bases[0];
			mixins = bases.length - t;
			superclass = bases[mixins];
		}else{
			bases = [0];
			if(superclass){
				if(opts.call(superclass) == "[object Function]"){
					t = superclass._meta;
					bases = bases.concat(t ? t.bases : superclass);
				}else{
					err("base class is not a callable constructor.", className);
				}
			}else if(superclass !== null){
				err("unknown base class. Did you use dojo.require to pull it in?", className);
			}
		}
		if(superclass){
			for(i = mixins - 1;; --i){
				proto = forceNew(superclass);
				if(!i){
					
					break;
				}
				
				t = bases[i];
				(t._meta ? mixOwn : mix)(proto, t.prototype);
				
				ctor = new Function;
				ctor.superclass = superclass;
				ctor.prototype = proto;
				superclass = proto.constructor = ctor;
			}
		}else{
			proto = {};
		}
		
		declare.safeMixin(proto, props);
		
		t = props.constructor;
		if(t !== op.constructor){
			t.nom = cname;
			proto.constructor = t;
		}

		
		for(i = mixins - 1; i; --i){ 
			t = bases[i]._meta;
			if(t && t.chains){
				chains = mix(chains || {}, t.chains);
			}
		}
		if(proto["-chains-"]){
			chains = mix(chains || {}, proto["-chains-"]);
		}

		
		t = !chains || !chains.hasOwnProperty(cname);
		bases[0] = ctor = (chains && chains.constructor === "manual") ? simpleConstructor(bases) :
			(bases.length == 1 ? singleConstructor(props.constructor, t) : chainedConstructor(bases, t));

		
		ctor._meta  = {bases: bases, hidden: props, chains: chains,
			parents: parents, ctor: props.constructor};
		ctor.superclass = superclass && superclass.prototype;
		ctor.extend = extend;
		ctor.prototype = proto;
		proto.constructor = ctor;

		
		proto.getInherited = getInherited;
		proto.isInstanceOf = isInstanceOf;
		proto.inherited    = inheritedImpl;
		proto.__inherited  = inherited;

		
		if(className){
			proto.declaredClass = className;
			lang.setObject(className, ctor);
		}

		
		if(chains){
			for(name in chains){
				if(proto[name] && typeof chains[name] == "string" && name != cname){
					t = proto[name] = chain(name, bases, chains[name] === "after");
					t.nom = name;
				}
			}
		}
		
		

		return ctor;	
	}

	/*=====
	dojo.declare = function(className, superclass, props){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//		(see http:
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|		
		
		//	|		
		
		
		
		//	|		
		
		
		
		
		
		
		
		//	|		
		//	|		
		
		
		//	|		
		//	|		
		
		
		//	|		
		//	|		
		
		
		//	|		
		//	|		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		//	|	
		//	|	
		//	|	
		//	|	
		//	|	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		//	|	
		//	|	
		//	|	
		//	|	
		
		
		
		
		
		
		
		
		
		//	|			
		//	|			
		
		//	|			
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		//	|	
		//	|	
		
		//	|	
		//	|	
		//	|	
		return new Function(); 
	};
	=====*/

	/*=====
	dojo.safeMixin = function(target, source){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		//	|	
		//	|	
		//	|	
	};
	=====*/

	/*=====
	Object.inherited = function(name, args, newArgs){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		//	|	
		
		
		
		
		
		
		
		
		//	|			
		
		
		
		
		
		
		
		return	{};	
	}
	=====*/

	/*=====
	Object.getInherited = function(name, args){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|			
		
		
		
		
		
		
		
		return	{};	
	}
	=====*/

	/*=====
	Object.isInstanceOf = function(cls){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|		
		//	|		
		
		
		//	|		
		//	|		
		
		
		//	|		
		//	|		
		
		
		//	|		
		//	|		
		
		
		
		
		//	|	console.log(a.isInstanceOf(A)); 
		//	|	console.log(b.isInstanceOf(A)); 
		//	|	console.log(c.isInstanceOf(A)); 
		//	|	console.log(d.isInstanceOf(A)); 
		
		//	|	console.log(a.isInstanceOf(B)); 
		//	|	console.log(b.isInstanceOf(B)); 
		//	|	console.log(c.isInstanceOf(B)); 
		//	|	console.log(d.isInstanceOf(B)); 
		
		//	|	console.log(a.isInstanceOf(C)); 
		//	|	console.log(b.isInstanceOf(C)); 
		//	|	console.log(c.isInstanceOf(C)); 
		//	|	console.log(d.isInstanceOf(C)); 
		
		//	|	console.log(a.isInstanceOf(D)); 
		//	|	console.log(b.isInstanceOf(D)); 
		//	|	console.log(c.isInstanceOf(D)); 
		//	|	console.log(d.isInstanceOf(D)); 
		return	{};	
	}
	=====*/

	/*=====
	Object.extend = function(source){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	dojo.safeMixin = declare.safeMixin = safeMixin;
	dojo.declare = declare;

	return declare;
});

},
'dojo/dom':function(){
define(["./_base/sniff", "./_base/lang", "./_base/window"],
		function(has, lang, win){
	
	
	
	

	

		try{
		document.execCommand("BackgroundImageCache", false, true);
	}catch(e){
		
	}
	
	
	
	

	/*=====
	dojo.byId = function(id, doc){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|		
		
	=====*/

	/*=====
	dojo.isDescendant = function(node, ancestor){
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	

	/*=====
	dojo.setSelectable = function(node, selectable){
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	var dom = {};   

		if(has("ie")){
		dom.byId = function(id, doc){
			if(typeof id != "string"){
				return id;
			}
			var _d = doc || win.doc, te = id && _d.getElementById(id);
			
			
			if(te && (te.attributes.id.value == id || te.id == id)){
				return te;
			}else{
				var eles = _d.all[id];
				if(!eles || eles.nodeName){
					eles = [eles];
				}
				
				var i = 0;
				while((te = eles[i++])){
					if((te.attributes && te.attributes.id && te.attributes.id.value == id) || te.id == id){
						return te;
					}
				}
			}
		};
	}else{
			dom.byId = function(id, doc){
			
			
			return ((typeof id == "string") ? (doc || win.doc).getElementById(id) : id) || null; 
		};
		}
		/*=====
	};
	=====*/

	dom.isDescendant = function(/*DOMNode|String*/node, /*DOMNode|String*/ancestor){
		try{
			node = dom.byId(node);
			ancestor = dom.byId(ancestor);
			while(node){
				if(node == ancestor){
					return true; 
				}
				node = node.parentNode;
			}
		}catch(e){ /* squelch, return false */ }
		return false; 
	};

	

	dom.setSelectable = function(/*DOMNode|String*/node, /*Boolean*/selectable){
		node = dom.byId(node);
				if(has("mozilla")){
			node.style.MozUserSelect = selectable ? "" : "none";
		}else if(has("khtml") || has("webkit")){
					node.style.KhtmlUserSelect = selectable ? "auto" : "none";
				}else if(has("ie")){
			var v = (node.unselectable = selectable ? "" : "on"),
				cs = node.getElementsByTagName("*"), i = 0, l = cs.length;
			for(; i < l; ++i){
				cs.item(i).unselectable = v;
			}
		}
				
	};

	return dom;
});

},
'dojo/_base/browser':function(){
if(require.has){
	require.has.add("config-selectorEngine", "acme");
}
define("dojo/_base/browser", [
	"../ready",
	"./kernel",
	"./connect", 
	"./unload",
	"./window",
	"./event",
	"./html",
	"./NodeList",
	"../query",
	"./xhr",
	"./fx"], function(dojo) {
	
	
	
	
	return dojo;
});

},
'dojo/selector/acme':function(){
define(["../_base/kernel", "../has", "../dom", "../_base/sniff", "../_base/array", "../_base/lang", "../_base/window"], function(dojo, has, dom){
  
  
  
  

/*
	acme architectural overview:

		acme is a relatively full-featured CSS3 query library. It is
		designed to take any valid CSS3 selector and return the nodes matching
		the selector. To do this quickly, it processes queries in several
		steps, applying caching where profitable.

		The steps (roughly in reverse order of the way they appear in the code):
			1.) check to see if we already have a "query dispatcher"
				- if so, use that with the given parameterization. Skip to step 4.
			2.) attempt to determine which branch to dispatch the query to:
				- JS (optimized DOM iteration)
				- native (FF3.1+, Safari 3.1+, IE 8+)
			3.) tokenize and convert to executable "query dispatcher"
				- this is where the lion's share of the complexity in the
					system lies. In the DOM version, the query dispatcher is
					assembled as a chain of "yes/no" test functions pertaining to
					a section of a simple query statement (".blah:nth-child(odd)"
					but not "div div", which is 2 simple statements). Individual
					statement dispatchers are cached (to prevent re-definition)
					as are entire dispatch chains (to make re-execution of the
					same query fast)
			4.) the resulting query dispatcher is called in the passed scope
					(by default the top-level document)
				- for DOM queries, this results in a recursive, top-down
					evaluation of nodes based on each simple query section
				- for native implementations, this may mean working around spec
					bugs. So be it.
			5.) matched nodes are pruned to ensure they are unique (if necessary)
*/


	//////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////////////

	
	
	
	
	var trim = 			dojo.trim;
	var each = 			dojo.forEach;
	// 					d.isIE; 
	// 					d.isSafari; 
	// 					d.isOpera; 
	// 					d.isWebKit; 
	// 					d.doc ; 

	var getDoc = function(){ return dojo.doc; };
	
	var cssCaseBug = ((dojo.isWebKit||dojo.isMozilla) && ((getDoc().compatMode) == "BackCompat"));

	//////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////////////


	var specials = ">~+";

	
	
	
	var caseSensitive = false;

	
	var yesman = function(){ return true; };

	//////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////////////

	var getQueryParts = function(query){
		
		
		
		
		
		
		
		
		
		
		
		
		
		


		
		
		
		
		
		
		
		if(specials.indexOf(query.slice(-1)) >= 0){
			
			
			query += " * "
		}else{
			
			
			query += " ";
		}

		var ts = function(/*Integer*/ s, /*Integer*/ e){
			

			
			
			return trim(query.slice(s, e));
		};

		
		var queryParts = [];


		
		var inBrackets = -1, inParens = -1, inMatchFor = -1,
			inPseudo = -1, inClass = -1, inId = -1, inTag = -1,
			lc = "", cc = "", pStart;

		
		var x = 0, 
			ql = query.length,
			currentPart = null, 
			_cp = null; 

		
		
		
		
		
		
		
		
		
		
		

		var endTag = function(){
			
			
			
			if(inTag >= 0){
				var tv = (inTag == x) ? null : ts(inTag, x); 
				currentPart[ (specials.indexOf(tv) < 0) ? "tag" : "oper" ] = tv;
				inTag = -1;
			}
		};

		var endId = function(){
			
			if(inId >= 0){
				currentPart.id = ts(inId, x).replace(/\\/g, "");
				inId = -1;
			}
		};

		var endClass = function(){
			
			
			
			if(inClass >= 0){
				currentPart.classes.push(ts(inClass + 1, x).replace(/\\/g, ""));
				inClass = -1;
			}
		};

		var endAll = function(){
			
			endId();
			endTag();
			endClass();
		};

		var endPart = function(){
			endAll();
			if(inPseudo >= 0){
				currentPart.pseudos.push({ name: ts(inPseudo + 1, x) });
			}
			
			
			
			
			currentPart.loops = (
					currentPart.pseudos.length ||
					currentPart.attrs.length ||
					currentPart.classes.length	);

			currentPart.oquery = currentPart.query = ts(pStart, x); 


			
			
			
			
			
			
			currentPart.otag = currentPart.tag = (currentPart["oper"]) ? null : (currentPart.tag || "*");

			if(currentPart.tag){
				
				
				
				
				currentPart.tag = currentPart.tag.toUpperCase();
			}

			
			if(queryParts.length && (queryParts[queryParts.length-1].oper)){
				
				
				
				currentPart.infixOper = queryParts.pop();
				currentPart.query = currentPart.infixOper.query + " " + currentPart.query;
				/*
				console.debug(	"swapping out the infix",
								currentPart.infixOper,
								"and attaching it to",
								currentPart);
				*/
			}
			queryParts.push(currentPart);

			currentPart = null;
		};

		
		
		for(; lc=cc, cc=query.charAt(x), x < ql; x++){
			
			

			
			
			if(lc == "\\"){ continue; }
			if(!currentPart){ 
				
				pStart = x;
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				currentPart = {
					query: null, 
					pseudos: [], 
					attrs: [],	
					classes: [], 
					tag: null,	
					oper: null, 
					id: null,	
					getTag: function(){
						return (caseSensitive) ? this.otag : this.tag;
					}
				};

				
				
				
				
				inTag = x;
			}

			if(inBrackets >= 0){
				
				if(cc == "]"){ 
					if(!_cp.attr){
						
						
						
						_cp.attr = ts(inBrackets+1, x);
					}else{
						
						
						_cp.matchFor = ts((inMatchFor||inBrackets+1), x);
					}
					var cmf = _cp.matchFor;
					if(cmf){
						
						
						
						if(	(cmf.charAt(0) == '"') || (cmf.charAt(0) == "'") ){
							_cp.matchFor = cmf.slice(1, -1);
						}
					}
					
					currentPart.attrs.push(_cp);
					_cp = null; 
					inBrackets = inMatchFor = -1;
				}else if(cc == "="){
					
					
					var addToCc = ("|~^$*".indexOf(lc) >=0 ) ? lc : "";
					_cp.type = addToCc+cc;
					_cp.attr = ts(inBrackets+1, x-addToCc.length);
					inMatchFor = x+1;
				}
				
			}else if(inParens >= 0){
				
				
				
				if(cc == ")"){
					if(inPseudo >= 0){
						_cp.value = ts(inParens+1, x);
					}
					inPseudo = inParens = -1;
				}
			}else if(cc == "#"){
				
				endAll();
				inId = x+1;
			}else if(cc == "."){
				
				endAll();
				inClass = x;
			}else if(cc == ":"){
				
				endAll();
				inPseudo = x;
			}else if(cc == "["){
				
				endAll();
				inBrackets = x;
				
				_cp = {
					/*=====
					attr: null, type: null, matchFor: null
					=====*/
				};
			}else if(cc == "("){
				
				
				if(inPseudo >= 0){
					
					_cp = {
						name: ts(inPseudo+1, x),
						value: null
					};
					currentPart.pseudos.push(_cp);
				}
				inParens = x;
			}else if(
				(cc == " ") &&
				
				
				(lc != cc)
			){
				endPart();
			}
		}
		return queryParts;
	};


	//////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////////////

	var agree = function(first, second){
		
		
		
		
		if(!first){ return second; }
		if(!second){ return first; }

		return function(){
			return first.apply(window, arguments) && second.apply(window, arguments);
		}
	};

	var getArr = function(i, arr){
		
		var r = arr||[]; 
		if(i){ r.push(i); }
		return r;
	};

	var _isElement = function(n){ return (1 == n.nodeType); };

	
	var blank = "";
	var _getAttr = function(elem, attr){
		if(!elem){ return blank; }
		if(attr == "class"){
			return elem.className || blank;
		}
		if(attr == "for"){
			return elem.htmlFor || blank;
		}
		if(attr == "style"){
			return elem.style.cssText || blank;
		}
		return (caseSensitive ? elem.getAttribute(attr) : elem.getAttribute(attr, 2)) || blank;
	};

	var attrs = {
		"*=": function(attr, value){
			return function(elem){
				
				
				
				return (_getAttr(elem, attr).indexOf(value)>=0);
			}
		},
		"^=": function(attr, value){
			
			
			
			return function(elem){
				return (_getAttr(elem, attr).indexOf(value)==0);
			}
		},
		"$=": function(attr, value){
			
			
			
			return function(elem){
				var ea = " "+_getAttr(elem, attr);
				return (ea.lastIndexOf(value)==(ea.length-value.length));
			}
		},
		"~=": function(attr, value){
			
			
			
			

			
			var tval = " "+value+" ";
			return function(elem){
				var ea = " "+_getAttr(elem, attr)+" ";
				return (ea.indexOf(tval)>=0);
			}
		},
		"|=": function(attr, value){
			
			
			
			
			var valueDash = value+"-";
			return function(elem){
				var ea = _getAttr(elem, attr);
				return (
					(ea == value) ||
					(ea.indexOf(valueDash)==0)
				);
			}
		},
		"=": function(attr, value){
			return function(elem){
				return (_getAttr(elem, attr) == value);
			}
		}
	};

	
	
	var _noNES = (typeof getDoc().firstChild.nextElementSibling == "undefined");
	var _ns = !_noNES ? "nextElementSibling" : "nextSibling";
	var _ps = !_noNES ? "previousElementSibling" : "previousSibling";
	var _simpleNodeTest = (_noNES ? _isElement : yesman);

	var _lookLeft = function(node){
		
		while(node = node[_ps]){
			if(_simpleNodeTest(node)){ return false; }
		}
		return true;
	};

	var _lookRight = function(node){
		
		while(node = node[_ns]){
			if(_simpleNodeTest(node)){ return false; }
		}
		return true;
	};

	var getNodeIndex = function(node){
		var root = node.parentNode;
		var i = 0,
			tret = root.children || root.childNodes,
			ci = (node["_i"]||-1),
			cl = (root["_l"]||-1);

		if(!tret){ return -1; }
		var l = tret.length;

		
		
		
		if( cl == l && ci >= 0 && cl >= 0 ){
			
			return ci;
		}

		
		root["_l"] = l;
		ci = -1;
		for(var te = root["firstElementChild"]||root["firstChild"]; te; te = te[_ns]){
			if(_simpleNodeTest(te)){
				te["_i"] = ++i;
				if(node === te){
					
					
					
					
					
					
					
					
					ci = i;
				}
			}
		}
		return ci;
	};

	var isEven = function(elem){
		return !((getNodeIndex(elem)) % 2);
	};

	var isOdd = function(elem){
		return ((getNodeIndex(elem)) % 2);
	};

	var pseudos = {
		"checked": function(name, condition){
			return function(elem){
				return !!("checked" in elem ? elem.checked : elem.selected);
			}
		},
		"first-child": function(){ return _lookLeft; },
		"last-child": function(){ return _lookRight; },
		"only-child": function(name, condition){
			return function(node){
				return _lookLeft(node) && _lookRight(node);
			};
		},
		"empty": function(name, condition){
			return function(elem){
				
				
				var cn = elem.childNodes;
				var cnl = elem.childNodes.length;
				
				for(var x=cnl-1; x >= 0; x--){
					var nt = cn[x].nodeType;
					if((nt === 1)||(nt == 3)){ return false; }
				}
				return true;
			}
		},
		"contains": function(name, condition){
			var cz = condition.charAt(0);
			if( cz == '"' || cz == "'" ){ 
				condition = condition.slice(1, -1);
			}
			return function(elem){
				return (elem.innerHTML.indexOf(condition) >= 0);
			}
		},
		"not": function(name, condition){
			var p = getQueryParts(condition)[0];
			var ignores = { el: 1 };
			if(p.tag != "*"){
				ignores.tag = 1;
			}
			if(!p.classes.length){
				ignores.classes = 1;
			}
			var ntf = getSimpleFilterFunc(p, ignores);
			return function(elem){
				return (!ntf(elem));
			}
		},
		"nth-child": function(name, condition){
			var pi = parseInt;
			
			if(condition == "odd"){
				return isOdd;
			}else if(condition == "even"){
				return isEven;
			}
			
			if(condition.indexOf("n") != -1){
				var tparts = condition.split("n", 2);
				var pred = tparts[0] ? ((tparts[0] == '-') ? -1 : pi(tparts[0])) : 1;
				var idx = tparts[1] ? pi(tparts[1]) : 0;
				var lb = 0, ub = -1;
				if(pred > 0){
					if(idx < 0){
						idx = (idx % pred) && (pred + (idx % pred));
					}else if(idx>0){
						if(idx >= pred){
							lb = idx - idx % pred;
						}
						idx = idx % pred;
					}
				}else if(pred<0){
					pred *= -1;
					
					
					if(idx > 0){
						ub = idx;
						idx = idx % pred;
					}
				}
				if(pred > 0){
					return function(elem){
						var i = getNodeIndex(elem);
						return (i>=lb) && (ub<0 || i<=ub) && ((i % pred) == idx);
					}
				}else{
					condition = idx;
				}
			}
			var ncount = pi(condition);
			return function(elem){
				return (getNodeIndex(elem) == ncount);
			}
		}
	};

	var defaultGetter = (dojo.isIE && (dojo.isIE < 9 || dojo.isQuirks)) ? function(cond){
		var clc = cond.toLowerCase();
		if(clc == "class"){ cond = "className"; }
		return function(elem){
			return (caseSensitive ? elem.getAttribute(cond) : elem[cond]||elem[clc]);
		}
	} : function(cond){
		return function(elem){
			return (elem && elem.getAttribute && elem.hasAttribute(cond));
		}
	};

	var getSimpleFilterFunc = function(query, ignores){
		
		
		
		
		
		
		
		if(!query){ return yesman; }
		ignores = ignores||{};

		var ff = null;

		if(!("el" in ignores)){
			ff = agree(ff, _isElement);
		}

		if(!("tag" in ignores)){
			if(query.tag != "*"){
				ff = agree(ff, function(elem){
					return (elem && (elem.tagName == query.getTag()));
				});
			}
		}

		if(!("classes" in ignores)){
			each(query.classes, function(cname, idx, arr){
				
				/*
				var isWildcard = cname.charAt(cname.length-1) == "*";
				if(isWildcard){
					cname = cname.substr(0, cname.length-1);
				}
				
				var re = new RegExp("(?:^|\\s)" + cname + (isWildcard ? ".*" : "") + "(?:\\s|$)");
				*/
				var re = new RegExp("(?:^|\\s)" + cname + "(?:\\s|$)");
				ff = agree(ff, function(elem){
					return re.test(elem.className);
				});
				ff.count = idx;
			});
		}

		if(!("pseudos" in ignores)){
			each(query.pseudos, function(pseudo){
				var pn = pseudo.name;
				if(pseudos[pn]){
					ff = agree(ff, pseudos[pn](pn, pseudo.value));
				}
			});
		}

		if(!("attrs" in ignores)){
			each(query.attrs, function(attr){
				var matcher;
				var a = attr.attr;
				
				if(attr.type && attrs[attr.type]){
					matcher = attrs[attr.type](a, attr.matchFor);
				}else if(a.length){
					matcher = defaultGetter(a);
				}
				if(matcher){
					ff = agree(ff, matcher);
				}
			});
		}

		if(!("id" in ignores)){
			if(query.id){
				ff = agree(ff, function(elem){
					return (!!elem && (elem.id == query.id));
				});
			}
		}

		if(!ff){
			if(!("default" in ignores)){
				ff = yesman;
			}
		}
		return ff;
	};

	var _nextSibling = function(filterFunc){
		return function(node, ret, bag){
			while(node = node[_ns]){
				if(_noNES && (!_isElement(node))){ continue; }
				if(
					(!bag || _isUnique(node, bag)) &&
					filterFunc(node)
				){
					ret.push(node);
				}
				break;
			}
			return ret;
		}
	};

	var _nextSiblings = function(filterFunc){
		return function(root, ret, bag){
			var te = root[_ns];
			while(te){
				if(_simpleNodeTest(te)){
					if(bag && !_isUnique(te, bag)){
						break;
					}
					if(filterFunc(te)){
						ret.push(te);
					}
				}
				te = te[_ns];
			}
			return ret;
		}
	};

	
	var _childElements = function(filterFunc){
		filterFunc = filterFunc||yesman;
		return function(root, ret, bag){
			
			var te, x = 0, tret = root.children || root.childNodes;
			while(te = tret[x++]){
				if(
					_simpleNodeTest(te) &&
					(!bag || _isUnique(te, bag)) &&
					(filterFunc(te, x))
				){
					ret.push(te);
				}
			}
			return ret;
		};
	};

	/*
	
	var itemIsAfterRoot = d.isIE ? function(item, root){
		return (item.sourceIndex > root.sourceIndex);
	} : function(item, root){
		return (item.compareDocumentPosition(root) == 2);
	};
	*/

	
	var _isDescendant = function(node, root){
		var pn = node.parentNode;
		while(pn){
			if(pn == root){
				break;
			}
			pn = pn.parentNode;
		}
		return !!pn;
	};

	var _getElementsFuncCache = {};

	var getElementsFunc = function(query){
		var retFunc = _getElementsFuncCache[query.query];
		
		if(retFunc){ return retFunc; }
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		var io = query.infixOper;
		var oper = (io ? io.oper : "");
		
		
		
		var filterFunc = getSimpleFilterFunc(query, { el: 1 });
		var qt = query.tag;
		var wildcardTag = ("*" == qt);
		var ecs = getDoc()["getElementsByClassName"];

		if(!oper){
			
			
			
			if(query.id){
				
				
				
				filterFunc = (!query.loops && wildcardTag) ?
					yesman :
					getSimpleFilterFunc(query, { el: 1, id: 1 });

				retFunc = function(root, arr){
					var te = dom.byId(query.id, (root.ownerDocument||root));
					if(!te || !filterFunc(te)){ return; }
					if(9 == root.nodeType){ 
						return getArr(te, arr);
					}else{ 
						if(_isDescendant(te, root)){
							return getArr(te, arr);
						}
					}
				}
			}else if(
				ecs &&
				
				/\{\s*\[native code\]\s*\}/.test(String(ecs)) &&
				query.classes.length &&
				!cssCaseBug
			){
				

				
				filterFunc = getSimpleFilterFunc(query, { el: 1, classes: 1, id: 1 });
				var classesString = query.classes.join(" ");
				retFunc = function(root, arr, bag){
					var ret = getArr(0, arr), te, x=0;
					var tret = root.getElementsByClassName(classesString);
					while((te = tret[x++])){
						if(filterFunc(te, root) && _isUnique(te, bag)){
							ret.push(te);
						}
					}
					return ret;
				};

			}else if(!wildcardTag && !query.loops){
				
				retFunc = function(root, arr, bag){
					var ret = getArr(0, arr), te, x=0;
					var tret = root.getElementsByTagName(query.getTag());
					while((te = tret[x++])){
						if(_isUnique(te, bag)){
							ret.push(te);
						}
					}
					return ret;
				};
			}else{
				
				
				
				
				filterFunc = getSimpleFilterFunc(query, { el: 1, tag: 1, id: 1 });
				retFunc = function(root, arr, bag){
					var ret = getArr(0, arr), te, x=0;
					
					var tret = root.getElementsByTagName(query.getTag());
					while((te = tret[x++])){
						if(filterFunc(te, root) && _isUnique(te, bag)){
							ret.push(te);
						}
					}
					return ret;
				};
			}
		}else{
			
			
			var skipFilters = { el: 1 };
			if(wildcardTag){
				skipFilters.tag = 1;
			}
			filterFunc = getSimpleFilterFunc(query, skipFilters);
			if("+" == oper){
				retFunc = _nextSibling(filterFunc);
			}else if("~" == oper){
				retFunc = _nextSiblings(filterFunc);
			}else if(">" == oper){
				retFunc = _childElements(filterFunc);
			}
		}
		
		return _getElementsFuncCache[query.query] = retFunc;
	};

	var filterDown = function(root, queryParts){
		
		
		
		
		var candidates = getArr(root), qp, x, te, qpl = queryParts.length, bag, ret;

		for(var i = 0; i < qpl; i++){
			ret = [];
			qp = queryParts[i];
			x = candidates.length - 1;
			if(x > 0){
				
				
				
				
				bag = {};
				ret.nozip = true;
			}
			var gef = getElementsFunc(qp);
			for(var j = 0; (te = candidates[j]); j++){
				
				
				
				
				
				gef(te, ret, bag);
			}
			if(!ret.length){ break; }
			candidates = ret;
		}
		return ret;
	};

	//////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////////////

	
	
	
	var _queryFuncCacheDOM = {},
		_queryFuncCacheQSA = {};

	
	
	
	var getStepQueryFunc = function(query){
		var qparts = getQueryParts(trim(query));

		
		if(qparts.length == 1){
			
			
			
			
			var tef = getElementsFunc(qparts[0]);
			return function(root){
				var r = tef(root, []);
				if(r){ r.nozip = true; }
				return r;
			}
		}

		
		return function(root){
			return filterDown(root, qparts);
		}
	};

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	var nua = navigator.userAgent;
	
	
	var wk = "WebKit/";
	var is525 = (
		dojo.isWebKit &&
		(nua.indexOf(wk) > 0) &&
		(parseFloat(nua.split(wk)[1]) > 528)
	);

	
	
	
	var noZip = dojo.isIE ? "commentStrip" : "nozip";

	var qsa = "querySelectorAll";
	var qsaAvail = (
		!!getDoc()[qsa] &&
		
		(!dojo.isSafari || (dojo.isSafari > 3.1) || is525 )
	);

	
	var infixSpaceRe = /n\+\d|([^ ])?([>~+])([^ =])?/g;
	var infixSpaceFunc = function(match, pre, ch, post){
		return ch ? (pre ? pre + " " : "") + ch + (post ? " " + post : "") : /*n+3*/ match;
	};

	var getQueryFunc = function(query, forceDOM){
		
		
		
		
		query = query.replace(infixSpaceRe, infixSpaceFunc);

		if(qsaAvail){
			
			var qsaCached = _queryFuncCacheQSA[query];
			if(qsaCached && !forceDOM){ return qsaCached; }
		}

		
		
		var domCached = _queryFuncCacheDOM[query];
		if(domCached){ return domCached; }

		
		
		
		

		var qcz = query.charAt(0);
		var nospace = (-1 == query.indexOf(" "));

		
		
		if( (query.indexOf("#") >= 0) && (nospace) ){
			forceDOM = true;
		}

		var useQSA = (
			qsaAvail && (!forceDOM) &&
			
			//		http:
			(specials.indexOf(qcz) == -1) &&
			
			(!dojo.isIE || (query.indexOf(":") == -1)) &&

			(!(cssCaseBug && (query.indexOf(".") >= 0))) &&

			
			
			
			
			
			
			//		http:
			(query.indexOf(":contains") == -1) && (query.indexOf(":checked") == -1) &&
			(query.indexOf("|=") == -1) 
		);

		
		
		
		
		
		


		if(useQSA){
			var tq = (specials.indexOf(query.charAt(query.length-1)) >= 0) ?
						(query + " *") : query;
			return _queryFuncCacheQSA[query] = function(root){
				try{
					
					
					
					//		http:
					
					
					
					
					
					if(!((9 == root.nodeType) || nospace)){ throw ""; }
					var r = root[qsa](tq);
					
					r[noZip] = true;
					return r;
				}catch(e){
					
					
					return getQueryFunc(query, true)(root);
				}
			}
		}else{
			
			var parts = query.split(/\s*,\s*/);
			return _queryFuncCacheDOM[query] = ((parts.length < 2) ?
				
				getStepQueryFunc(query) :
				
				
				
				function(root){
					var pindex = 0, 
						ret = [],
						tp;
					while((tp = parts[pindex++])){
						ret = ret.concat(getStepQueryFunc(tp)(root));
					}
					return ret;
				}
			);
		}
	};

	var _zipIdx = 0;

	
	
	
	var _nodeUID = dojo.isIE ? function(node){
		if(caseSensitive){
			
			return (node.getAttribute("_uid") || node.setAttribute("_uid", ++_zipIdx) || _zipIdx);

		}else{
			return node.uniqueID;
		}
	} :
	function(node){
		return (node._uid || (node._uid = ++_zipIdx));
	};

	
	
	
	
	
	var _isUnique = function(node, bag){
		if(!bag){ return 1; }
		var id = _nodeUID(node);
		if(!bag[id]){ return bag[id] = 1; }
		return 0;
	};

	
	
	var _zipIdxName = "_zipIdx";
	var _zip = function(arr){
		if(arr && arr.nozip){
			return arr;
		}
		var ret = [];
		if(!arr || !arr.length){ return ret; }
		if(arr[0]){
			ret.push(arr[0]);
		}
		if(arr.length < 2){ return ret; }

		_zipIdx++;

		
		
		if(dojo.isIE && caseSensitive){
			var szidx = _zipIdx+"";
			arr[0].setAttribute(_zipIdxName, szidx);
			for(var x = 1, te; te = arr[x]; x++){
				if(arr[x].getAttribute(_zipIdxName) != szidx){
					ret.push(te);
				}
				te.setAttribute(_zipIdxName, szidx);
			}
		}else if(dojo.isIE && arr.commentStrip){
			try{
				for(var x = 1, te; te = arr[x]; x++){
					if(_isElement(te)){
						ret.push(te);
					}
				}
			}catch(e){ /* squelch */ }
		}else{
			if(arr[0]){ arr[0][_zipIdxName] = _zipIdx; }
			for(var x = 1, te; te = arr[x]; x++){
				if(arr[x][_zipIdxName] != _zipIdx){
					ret.push(te);
				}
				te[_zipIdxName] = _zipIdx;
			}
		}
		return ret;
	};

	
	var query = function(/*String*/ query, /*String|DOMNode?*/ root){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//		CSS3 selectors, see <http:
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|		dojo.stopEvent(e); 
		
		
		
		
		//	|				
		
		
		
		
		
		
		

		root = root||getDoc();
		var od = root.ownerDocument||root.documentElement;

		

		
		
		
		caseSensitive = (root.contentType && root.contentType=="application/xml") ||
						(dojo.isOpera && (root.doctype || od.toString() == "[object XMLDocument]")) ||
						(!!od) &&
				(dojo.isIE ? od.xml : (root.xmlVersion || od.xmlVersion));

		
		
		
		
		var r = getQueryFunc(query)(root);

		
		
		if(r && r.nozip){
			return r;
		}
		return _zip(r); 
	};
	query.filter = function(/*Node[]*/ nodeList, /*String*/ filter, /*String|DOMNode?*/ root){
		
		
		var tmpNodeList = [],
			parts = getQueryParts(filter),
			filterFunc =
				(parts.length == 1 && !/[^\w#\.]/.test(filter)) ?
				getSimpleFilterFunc(parts[0]) :
				function(node){
					return dojo.query(filter, root).indexOf(node) != -1;
				};
		for(var x = 0, te; te = nodeList[x]; x++){
			if(filterFunc(te)){ tmpNodeList.push(te); }
		}
		return tmpNodeList;
	};
	return query;
});

},
'dojo/dom-style':function(){
define(["./_base/sniff", "./dom"], function(has, dom){
	
	
	
	

	
	
	

	
	
	
	
	
	
	
	

	/*=====
	dojo.getComputedStyle = function(node){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return; 
	}
	=====*/

	/*=====
	dojo.toPixelValue = function(node, value){
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo._toPixelValue = function(node, value){
		
		
	};
	=====*/

	/*=====
	dojo.getStyle = function(node, name){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	dojo.getStyle("thinger", "opacity"); 
	};
	=====*/

	/*=====
	dojo.setStyle = function(node, name, value){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	dojo.setStyle("thinger", "opacity", 0.5); 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
		
		
		
	};
	=====*/

	
	
	
	
	
	var getComputedStyle, style = {};
		if(has("webkit")){
			getComputedStyle = function(/*DomNode*/node){
			var s;
			if(node.nodeType == 1){
				var dv = node.ownerDocument.defaultView;
				s = dv.getComputedStyle(node, null);
				if(!s && node.style){
					node.style.display = "";
					s = dv.getComputedStyle(node, null);
				}
			}
			return s || {};
		};
		}else if(has("ie") && (has("ie") < 9 || has("quirks"))){
		getComputedStyle = function(node){
			
			return node.nodeType == 1 /* ELEMENT_NODE*/ ? node.currentStyle : {};
		};
	}else{
		getComputedStyle = function(node){
			return node.nodeType == 1 ?
				node.ownerDocument.defaultView.getComputedStyle(node, null) : {};
		};
	}
		style.getComputedStyle = getComputedStyle;

	var toPixel;
		if(!has("ie")){
			toPixel = function(element, value){
			
			
			return parseFloat(value) || 0;
		};
		}else{
		toPixel = function(element, avalue){
			if(!avalue){ return 0; }
			
			if(avalue == "medium"){ return 4; }
			
			
			if(avalue.slice && avalue.slice(-2) == 'px'){ return parseFloat(avalue); }
			var s = element.style, rs = element.runtimeStyle, cs = element.currentStyle,
				sLeft = s.left, rsLeft = rs.left;
			rs.left = cs.left;
			try{
				
				
				
				
				s.left = avalue;
				avalue = s.pixelLeft;
			}catch(e){
				avalue = 0;
			}
			s.left = sLeft;
			rs.left = rsLeft;
			return avalue;
		}
	}
		style.toPixelValue = toPixel;

	

		var astr = "DXImageTransform.Microsoft.Alpha";
	var af = function(n, f){
		try{
			return n.filters.item(astr);
		}catch(e){
			return f ? {} : null;
		}
	};

		var _getOpacity =
			has("ie") < 9 || (has("ie") && has("quirks")) ? function(node){
			try{
				return af(node).Opacity / 100; 
			}catch(e){
				return 1; 
			}
		} :
			function(node){
			return getComputedStyle(node).opacity;
		};

	var _setOpacity =
				has("ie") < 9 || (has("ie") && has("quirks")) ? function(/*DomNode*/node, /*Number*/opacity){
			var ov = opacity * 100, opaque = opacity == 1;
			node.style.zoom = opaque ? "" : 1;

			if(!af(node)){
				if(opaque){
					return opacity;
				}
				node.style.filter += " progid:" + astr + "(Opacity=" + ov + ")";
			}else{
				af(node, 1).Opacity = ov;
			}

			
			
			af(node, 1).Enabled = !opaque;

			if(node.tagName.toLowerCase() == "tr"){
				for(var td = node.firstChild; td; td = td.nextSibling){
					if(td.tagName.toLowerCase() == "td"){
						_setOpacity(td, opacity);
					}
				}
			}
			return opacity;
		} :
				function(node, opacity){
			return node.style.opacity = opacity;
		};

	var _pixelNamesCache = {
		left: true, top: true
	};
	var _pixelRegExp = /margin|padding|width|height|max|min|offset/; 
	function _toStyleValue(node, type, value){
		
		type = type.toLowerCase();
				if(has("ie")){
			if(value == "auto"){
				if(type == "height"){ return node.offsetHeight; }
				if(type == "width"){ return node.offsetWidth; }
			}
			if(type == "fontweight"){
				switch(value){
					case 700: return "bold";
					case 400:
					default: return "normal";
				}
			}
		}
				if(!(type in _pixelNamesCache)){
			_pixelNamesCache[type] = _pixelRegExp.test(type);
		}
		return _pixelNamesCache[type] ? toPixel(node, value) : value;
	}

	var _floatStyle = has("ie") ? "styleFloat" : "cssFloat",
		_floatAliases = {"cssFloat": _floatStyle, "styleFloat": _floatStyle, "float": _floatStyle};

	

	style.get = function getStyle(/*DOMNode|String*/ node, /*String?*/ name){
		var n = dom.byId(node), l = arguments.length, op = (name == "opacity");
		if(l == 2 && op){
			return _getOpacity(n);
		}
		name = _floatAliases[name] || name;
		var s = style.getComputedStyle(n);
		return (l == 1) ? s : _toStyleValue(n, name, s[name] || n.style[name]); /* CSS2Properties||String||Number */
	};

	style.set = function setStyle(/*DOMNode|String*/ node, /*String|Object*/ name, /*String?*/ value){
		var n = dom.byId(node), l = arguments.length, op = (name == "opacity");
		name = _floatAliases[name] || name;
		if(l == 3){
			return op ? _setOpacity(n, value) : n.style[name] = value; 
		}
		for(var x in name){
			style.set(node, x, name[x]);
		}
		return style.getComputedStyle(n);
	};

	return style;
});

},
'dojo/dom-geometry':function(){
define(["./_base/sniff", "./_base/window","./dom", "./dom-style"],
		function(has, win, dom, style){
	
	
	
	

	var geom = {};  

	
	
	

	
	
	
	geom.boxModel = "content-box";

	
	
	

	
	
	
	

		if(has("ie") /*|| has("opera")*/){
		
		geom.boxModel = document.compatMode == "BackCompat" ? "border-box" : "content-box";
	}
	
	
	
	

	/*=====
	dojo.getPadExtents = function(node, computedStyle){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		


	};
	=====*/

	/*=====
	dojo._getPadExtents = function(node, computedStyle){
		
		
	};
	=====*/

	/*=====
	dojo.getBorderExtents = function(node, computedStyle){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		


	};
	=====*/

	/*=====
	dojo._getBorderExtents = function(node, computedStyle){
		
		
	};
	=====*/

	/*=====
	dojo.getPadBorderExtents = function(node, computedStyle){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		


	};
	=====*/

	/*=====
	dojo._getPadBorderExtents = function(node, computedStyle){
		
		
	};
	=====*/

	/*=====
	dojo.getMarginExtents = function(node, computedStyle){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo._getMarginExtents = function(node, computedStyle){
		
		
	};
	=====*/

	/*=====
	dojo.getMarginSize = function(node, computedStyle){
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo._getMarginSize = function(node, computedStyle){
		
		
	};
	=====*/

	/*=====
	dojo.getMarginBox = function(node, computedStyle){
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo._getMarginBox = function(node, computedStyle){
		
		
	};
	=====*/

	/*=====
	dojo.setMarginBox = function(node, box, computedStyle){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo.getContentBox = function(node, computedStyle){
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo._getContentBox = function(node, computedStyle){
		
		
	};
	=====*/

	/*=====
	dojo.setContentSize = function(node, box, computedStyle){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo.isBodyLtr = function(){
		
		
		
	};
	=====*/

	/*=====
	dojo._isBodyLtr = function(){
		
		
	};
	=====*/

	/*=====
	dojo.docScroll = function(){
		
		
		
	};
	=====*/

	/*=====
	dojo._docScroll = function(){
		
		
	};
	=====*/

	/*=====
	dojo.getIeDocumentElementOffset = function(){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo._getIeDocumentElementOffset = function(){
		
		
	};
	=====*/

	/*=====
	dojo.fixIeBiDiScrollLeft = function(scrollLeft){
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo._fixIeBiDiScrollLeft = function(scrollLeft){
		
		
	};
	=====*/

	/*=====
	dojo.position = function(node, includeScroll){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	geom.getPadExtents = function getPadExtents(/*DomNode*/node, /*Object*/computedStyle){
		node = dom.byId(node);
		var s = computedStyle || style.getComputedStyle(node), px = style.toPixelValue,
			l = px(node, s.paddingLeft), t = px(node, s.paddingTop), r = px(node, s.paddingRight), b = px(node, s.paddingBottom);
		return {l: l, t: t, r: r, b: b, w: l + r, h: t + b};
	};

	var none = "none";

	geom.getBorderExtents = function getBorderExtents(/*DomNode*/node, /*Object*/computedStyle){
		node = dom.byId(node);
		var px = style.toPixelValue, s = computedStyle || style.getComputedStyle(node),
			l = s.borderLeftStyle != none ? px(node, s.borderLeftWidth) : 0,
			t = s.borderTopStyle != none ? px(node, s.borderTopWidth) : 0,
			r = s.borderRightStyle != none ? px(node, s.borderRightWidth) : 0,
			b = s.borderBottomStyle != none ? px(node, s.borderBottomWidth) : 0;
		return {l: l, t: t, r: r, b: b, w: l + r, h: t + b};
	};

	geom.getPadBorderExtents = function getPadBorderExtents(/*DomNode*/node, /*Object*/computedStyle){
		node = dom.byId(node);
		var s = computedStyle || style.getComputedStyle(node),
			p = geom.getPadExtents(node, s),
			b = geom.getBorderExtents(node, s);
		return {
			l: p.l + b.l,
			t: p.t + b.t,
			r: p.r + b.r,
			b: p.b + b.b,
			w: p.w + b.w,
			h: p.h + b.h
		};
	};

	geom.getMarginExtents = function getMarginExtents(node, computedStyle){
		node = dom.byId(node);
		var s = computedStyle || style.getComputedStyle(node), px = style.toPixelValue,
			l = px(node, s.marginLeft), t = px(node, s.marginTop), r = px(node, s.marginRight), b = px(node, s.marginBottom);
		if(has("webkit") && (s.position != "absolute")){
			
			
			
			
			
			
			r = l;
		}
		return {l: l, t: t, r: r, b: b, w: l + r, h: t + b};
	};

	
	
	
	
	
	
	
	

	
	
	
	
	

	geom.getMarginBox = function getMarginBox(/*DomNode*/node, /*Object*/computedStyle){
		
		
		
		node = dom.byId(node);
		var s = computedStyle || style.getComputedStyle(node), me = geom.getMarginExtents(node, s),
			l = node.offsetLeft - me.l, t = node.offsetTop - me.t, p = node.parentNode, px = style.toPixelValue, pcs;
				if(has("mozilla")){
			
			
			
			
			
			var sl = parseFloat(s.left), st = parseFloat(s.top);
			if(!isNaN(sl) && !isNaN(st)){
				l = sl, t = st;
			}else{
				
				
				if(p && p.style){
					pcs = style.getComputedStyle(p);
					if(pcs.overflow != "visible"){
						l += pcs.borderLeftStyle != none ? px(node, pcs.borderLeftWidth) : 0;
						t += pcs.borderTopStyle != none ? px(node, pcs.borderTopWidth) : 0;
					}
				}
			}
		}else if(has("opera") || (has("ie") == 8 && !has("quirks"))){
			
			if(p){
				pcs = style.getComputedStyle(p);
				l -= pcs.borderLeftStyle != none ? px(node, pcs.borderLeftWidth) : 0;
				t -= pcs.borderTopStyle != none ? px(node, pcs.borderTopWidth) : 0;
			}
		}
				return {l: l, t: t, w: node.offsetWidth + me.w, h: node.offsetHeight + me.h};
	};

	geom.getContentBox = function getContentBox(node, computedStyle){
		
		
		node = dom.byId(node);
		var s = computedStyle || style.getComputedStyle(node), w = node.clientWidth, h,
			pe = geom.getPadExtents(node, s), be = geom.getBorderExtents(node, s);
		if(!w){
			w = node.offsetWidth;
			h = node.offsetHeight;
		}else{
			h = node.clientHeight;
			be.w = be.h = 0;
		}
		
				if(has("opera")){
			pe.l += be.l;
			pe.t += be.t;
		}
				return {l: pe.l, t: pe.t, w: w - pe.w - be.w, h: h - pe.h - be.h};
	};

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	function setBox(/*DomNode*/node, /*Number?*/l, /*Number?*/t, /*Number?*/w, /*Number?*/h, /*String?*/u){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		u = u || "px";
		var s = node.style;
		if(!isNaN(l)){
			s.left = l + u;
		}
		if(!isNaN(t)){
			s.top = t + u;
		}
		if(w >= 0){
			s.width = w + u;
		}
		if(h >= 0){
			s.height = h + u;
		}
	}

	function isButtonTag(/*DomNode*/node){
		
		
		return node.tagName.toLowerCase() == "button" ||
			node.tagName.toLowerCase() == "input" && (node.getAttribute("type") || "").toLowerCase() == "button"; 
	}

	function usesBorderBox(/*DomNode*/node){
		
		

		
		

		
		
		

		return geom.boxModel == "border-box" || node.tagName.toLowerCase() == "table" || isButtonTag(node); 
	}

	geom.setContentSize = function setContentSize(/*DomNode*/node, /*Object*/box, /*Object*/computedStyle){
		
		
		

		node = dom.byId(node);
		var w = box.w, h = box.h;
		if(usesBorderBox(node)){
			var pb = geom.getPadBorderExtents(node, computedStyle);
			if(w >= 0){
				w += pb.w;
			}
			if(h >= 0){
				h += pb.h;
			}
		}
		setBox(node, NaN, NaN, w, h);
	};

	var nilExtents = {l: 0, t: 0, w: 0, h: 0};

	geom.setMarginBox = function setMarginBox(/*DomNode*/node, /*Object*/box, /*Object*/computedStyle){
		node = dom.byId(node);
		var s = computedStyle || style.getComputedStyle(node), w = box.w, h = box.h,
		
		
		
			pb = usesBorderBox(node) ? nilExtents : geom.getPadBorderExtents(node, s),
			mb = geom.getMarginExtents(node, s);
		if(has("webkit")){
			
			
			
			if(isButtonTag(node)){
				var ns = node.style;
				if(w >= 0 && !ns.width){
					ns.width = "4px";
				}
				if(h >= 0 && !ns.height){
					ns.height = "4px";
				}
			}
		}
		if(w >= 0){
			w = Math.max(w - pb.w - mb.w, 0);
		}
		if(h >= 0){
			h = Math.max(h - pb.h - mb.h, 0);
		}
		setBox(node, box.l, box.t, w, h);
	};

	
	
	

	geom.isBodyLtr = function isBodyLtr(){
		return (win.body().dir || win.doc.documentElement.dir || "ltr").toLowerCase() == "ltr"; 
	};

	geom.docScroll = function docScroll(){
		var node = win.doc.parentWindow || win.doc.defaultView;   
		return "pageXOffset" in node ? {x: node.pageXOffset, y: node.pageYOffset } :
			(node = has("quirks") ? win.body() : win.doc.documentElement,
				{x: geom.fixIeBiDiScrollLeft(node.scrollLeft || 0), y: node.scrollTop || 0 });
	};

		geom.getIeDocumentElementOffset = function getIeDocumentElementOffset(){
		

		var de = win.doc.documentElement; 

		if(has("ie") < 8){
			var r = de.getBoundingClientRect(), 
				l = r.left, t = r.top;
			if(has("ie") < 7){
				l += de.clientLeft;	
				t += de.clientTop;	
			}
			return {
				x: l < 0 ? 0 : l, 
				y: t < 0 ? 0 : t
			};
		}else{
			return {
				x: 0,
				y: 0
			};
		}
	};
	
	geom.fixIeBiDiScrollLeft = function fixIeBiDiScrollLeft(/*Integer*/ scrollLeft){
		
		
		
		

				var ie = has("ie");
		if(ie && !geom.isBodyLtr()){
			var qk = has("quirks"),
				de = qk ? win.body() : win.doc.documentElement;
			if(ie == 6 && !qk && win.global.frameElement && de.scrollHeight > de.clientHeight){
				scrollLeft += de.clientLeft; 
			}
			return (ie < 8 || qk) ? (scrollLeft + de.clientWidth - de.scrollWidth) : -scrollLeft; 
		}
				return scrollLeft; 
	};

	geom.position = function(/*DomNode*/node, /*Boolean?*/includeScroll){
		node = dom.byId(node);
		var	db = win.body(),
			dh = db.parentNode,
			ret = node.getBoundingClientRect();
		ret = {x: ret.left, y: ret.top, w: ret.right - ret.left, h: ret.bottom - ret.top};
				if(has("ie")){
			
			var offset = geom.getIeDocumentElementOffset();

			
			ret.x -= offset.x + (has("quirks") ? db.clientLeft + db.offsetLeft : 0);
			ret.y -= offset.y + (has("quirks") ? db.clientTop + db.offsetTop : 0);
		}else if(has("ff") == 3){
			
			
			var cs = style.getComputedStyle(dh), px = style.toPixelValue;
			ret.x -= px(dh, cs.marginLeft) + px(dh, cs.borderLeftWidth);
			ret.y -= px(dh, cs.marginTop) + px(dh, cs.borderTopWidth);
		}
				
		
		
		if(includeScroll){
			var scroll = geom.docScroll();
			ret.x += scroll.x;
			ret.y += scroll.y;
		}

		return ret; 
	};

	

	geom.getMarginSize = function getMarginSize(/*DomNode*/node, /*Object*/computedStyle){
		node = dom.byId(node);
		var me = geom.getMarginExtents(node, computedStyle || style.getComputedStyle(node));
		var size = node.getBoundingClientRect();
		return {
			w: (size.right - size.left) + me.w,
			h: (size.bottom - size.top) + me.h
		}
	};

	geom.normalizeEvent = function(event){
		
		
		
		
		if(!("layerX" in event)){
			event.layerX = event.offsetX;
			event.layerY = event.offsetY;
		}
		if(!has("dom-addeventlistener")){
			
			
			
			
			var se = event.target;
			var doc = (se && se.ownerDocument) || document;
			
			
			var docBody = has("quirks") ? doc.body : doc.documentElement;
			var offset = geom.getIeDocumentElementOffset();
			event.pageX = event.clientX + geom.fixIeBiDiScrollLeft(docBody.scrollLeft || 0) - offset.x;
			event.pageY = event.clientY + (docBody.scrollTop || 0) - offset.y;
		}
	};

	

	return geom;
});

},
'dojo/dom-prop':function(){
define("dojo/dom-prop", ["exports", "./_base/kernel", "./_base/sniff", "./_base/lang", "./dom", "./dom-style", "./dom-construct", "./_base/connect"],
		function(exports, dojo, has, lang, dom, style, ctr, conn){
	
	
	
	
	

	
	
	

	/*=====
	prop.get = function(node, name){
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
		//	|	
		
	};
	=====*/

	/*=====
	prop.set = function(node, name, value){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
		
		
		
		
		
		
		
		
		
		//	|			
		//	|			
		//	|			
		//	|			
		//	|			
		//	|			
		
		
		//	|			
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
	};
	=====*/

	
	var _evtHdlrMap = {}, _ctr = 0, _attrId = dojo._scopeName + "attrid";

		
	var _roInnerHtml = {col: 1, colgroup: 1,
			
			table: 1, tbody: 1, tfoot: 1, thead: 1, tr: 1, title: 1};
	
	exports.names = {
		
		"class": "className",
		"for": "htmlFor",
		
		tabindex: "tabIndex",
		readonly: "readOnly",
		colspan: "colSpan",
		frameborder: "frameBorder",
		rowspan: "rowSpan",
		valuetype: "valueType"
	};

	exports.get = function getProp(/*DOMNode|String*/node, /*String*/name){
		node = dom.byId(node);
		var lc = name.toLowerCase(), propName = exports.names[lc] || name;
		return node[propName];	
	};

	exports.set = function setProp(/*DOMNode|String*/node, /*String|Object*/name, /*String?*/value){
		node = dom.byId(node);
		var l = arguments.length;
		if(l == 2 && typeof name != "string"){ 
			
			for(var x in name){
				exports.set(node, x, name[x]);
			}
			return node; 
		}
		var lc = name.toLowerCase(), propName = exports.names[lc] || name;
		if(propName == "style" && typeof value != "string"){ 
			
			style.style(node, value);
			return node; 
		}
		if(propName == "innerHTML"){
			
						if(has("ie") && node.tagName.toLowerCase() in _roInnerHtml){
				ctr.empty(node);
				node.appendChild(ctr.toDom(value, node.ownerDocument));
			}else{
							node[propName] = value;
						}
						return node; 
		}
		if(lang.isFunction(value)){
			
			
			var attrId = node[_attrId];
			if(!attrId){
				attrId = _ctr++;
				node[_attrId] = attrId;
			}
			if(!_evtHdlrMap[attrId]){
				_evtHdlrMap[attrId] = {};
			}
			var h = _evtHdlrMap[attrId][propName];
			if(h){
				
				conn.disconnect(h);
			}else{
				try{
					delete node[propName];
				}catch(e){}
			}
			
			if(value){
				
				_evtHdlrMap[attrId][propName] = conn.connect(node, propName, value);
			}else{
				node[propName] = null;
			}
			return node; 
		}
		node[propName] = value;
		return node;	
	};
});

},
'dojo/dom-attr':function(){
define(["exports", "./_base/sniff", "./_base/lang", "./dom", "./dom-style", "./dom-prop"],
		function(exports, has, lang, dom, style, prop){
	
	
	
	

	
	
	

	

	// dojo.attr() should conform to http:

	

	/*=====
	dojo.hasAttr = function(node, name){
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo.getAttr = function(node, name){
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
		//	|	
		
	};
	=====*/

	/*=====
	dojo.setAttr = function(node, name, value){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
		
		
		
		
		
		
		
		
		//	|			
		//	|			
		//	|			
		//	|			
		//	|			
		//	|			
		
		
		//	|			
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
	};
	=====*/

	/*=====
	dojo.removeAttr = function(node, name){
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo.getNodeProp = function(node, name){
		
		
		
		
		
		
		
		
	};
	=====*/

	var forcePropNames = {
			innerHTML:	1,
			className:	1,
			htmlFor:	has("ie"),
			value:		1
		},
		attrNames = {
			
			classname: "class",
			htmlfor: "for",
			
			tabindex: "tabIndex",
			readonly: "readOnly"
		};

	function _hasAttr(node, name){
		var attr = node.getAttributeNode && node.getAttributeNode(name);
		return attr && attr.specified; 
	}

	
	
	
	

	exports.has = function hasAttr(/*DOMNode|String*/node, /*String*/name){
		var lc = name.toLowerCase();
		return forcePropNames[prop.names[lc] || name] || _hasAttr(dom.byId(node), attrNames[lc] || name);	
	};

	exports.get = function getAttr(/*DOMNode|String*/node, /*String*/name){
		node = dom.byId(node);
		var lc = name.toLowerCase(),
			propName = prop.names[lc] || name,
			forceProp = forcePropNames[propName];
		
		value = node[propName];
		if(forceProp && typeof value != "undefined"){
			
			return value;	
		}
		if(propName != "href" && (typeof value == "boolean" || lang.isFunction(value))){
			
			return value;	
		}
		
		
		var attrName = attrNames[lc] || name;
		return _hasAttr(node, attrName) ? node.getAttribute(attrName) : null; 
	};

	exports.set = function setAttr(/*DOMNode|String*/node, /*String|Object*/name, /*String?*/value){
		node = dom.byId(node);
		if(arguments.length == 2){ 
			
			for(var x in name){
				exports.set(node, x, name[x]);
			}
			return node; 
		}
		var lc = name.toLowerCase(),
			propName = prop.names[lc] || name,
			forceProp = forcePropNames[propName];
		if(propName == "style" && typeof value != "string"){ 
			
			style.set(node, value);
			return node; 
		}
		if(forceProp || typeof value == "boolean" || lang.isFunction(value)){
			return prop.set(node, name, value)
		}
		
		node.setAttribute(attrNames[lc] || name, value);
		return node; 
	};

	exports.remove = function removeAttr(/*DOMNode|String*/ node, /*String*/ name){
		dom.byId(node).removeAttribute(attrNames[name.toLowerCase()] || name);
	};

	exports.getNodeProp = function getNodeProp(/*DomNode|String*/ node, /*String*/ name){
		node = dom.byId(node);
		var lc = name.toLowerCase(), propName = prop.names[lc] || name;
		if((propName in node) && propName != "href"){
			
			return node[propName];	
		}
		
		var attrName = attrNames[lc] || name;
		return _hasAttr(node, attrName) ? node.getAttribute(attrName) : null; 
	};
});

},
'dojo/dom-construct':function(){
define("dojo/dom-construct", ["exports", "./_base/kernel", "./_base/sniff", "./_base/window", "./dom", "./dom-attr", "./on"],
		function(exports, dojo, has, win, dom, attr, on){
	
	
	
	

	/*=====
	dojo.toDom = function(frag, doc){
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo._toDom = function(frag, doc){
		
		
	};
	=====*/

	/*=====
	dojo.place = function(node, refNode, position){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo.create = function(tag, attrs, refNode, pos){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|		.place("#someNode"); 
	};
	=====*/

	/*=====
	dojo.empty = function(node){
			
			
			
			
			
			
			
			
			
			
			
	}
	=====*/

	/*=====
	dojo.destroy = function(node){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo._destroyElement = function(node){
		
		
	};
	=====*/

	
	var tagWrap = {
			option: ["select"],
			tbody: ["table"],
			thead: ["table"],
			tfoot: ["table"],
			tr: ["table", "tbody"],
			td: ["table", "tbody", "tr"],
			th: ["table", "thead", "tr"],
			legend: ["fieldset"],
			caption: ["table"],
			colgroup: ["table"],
			col: ["table", "colgroup"],
			li: ["ul"]
		},
		reTag = /<\s*([\w\:]+)/,
		masterNode = {}, masterNum = 0,
		masterName = "__" + dojo._scopeName + "ToDomId";

	
	
	for(var param in tagWrap){
		if(tagWrap.hasOwnProperty(param)){
			var tw = tagWrap[param];
			tw.pre = param == "option" ? '<select multiple="multiple">' : "<" + tw.join("><") + ">";
			tw.post = "</" + tw.reverse().join("></") + ">";
			
			
		}
	}

	function _insertBefore(/*DomNode*/node, /*DomNode*/ref){
		var parent = ref.parentNode;
		if(parent){
			parent.insertBefore(node, ref);
		}
	}

	function _insertAfter(/*DomNode*/node, /*DomNode*/ref){
		
		
		var parent = ref.parentNode;
		if(parent){
			if(parent.lastChild == ref){
				parent.appendChild(node);
			}else{
				parent.insertBefore(node, ref.nextSibling);
			}
		}
	}

	var _destroyContainer = null,
		_destroyDoc;
		on(window, "unload", function(){
		_destroyContainer = null; 
	});
	
	exports.toDom = function toDom(frag, doc){
		doc = doc || win.doc;
		var masterId = doc[masterName];
		if(!masterId){
			doc[masterName] = masterId = ++masterNum + "";
			masterNode[masterId] = doc.createElement("div");
		}

		
		frag += "";

		
		var match = frag.match(reTag),
			tag = match ? match[1].toLowerCase() : "",
			master = masterNode[masterId],
			wrap, i, fc, df;
		if(match && tagWrap[tag]){
			wrap = tagWrap[tag];
			master.innerHTML = wrap.pre + frag + wrap.post;
			for(i = wrap.length; i; --i){
				master = master.firstChild;
			}
		}else{
			master.innerHTML = frag;
		}

		
		if(master.childNodes.length == 1){
			return master.removeChild(master.firstChild); 
		}

		
		df = doc.createDocumentFragment();
		while(fc = master.firstChild){ 
			df.appendChild(fc);
		}
		return df; 
	};

	exports.place = function place(/*DOMNode|String*/node, /*DOMNode|String*/refNode, /*String|Number?*/position){
		refNode = dom.byId(refNode);
		if(typeof node == "string"){ 
			node = /^\s*</.test(node) ? exports.toDom(node, refNode.ownerDocument) : dom.byId(node);
		}
		if(typeof position == "number"){ 
			var cn = refNode.childNodes;
			if(!cn.length || cn.length <= position){
				refNode.appendChild(node);
			}else{
				_insertBefore(node, cn[position < 0 ? 0 : position]);
			}
		}else{
			switch(position){
				case "before":
					_insertBefore(node, refNode);
					break;
				case "after":
					_insertAfter(node, refNode);
					break;
				case "replace":
					refNode.parentNode.replaceChild(node, refNode);
					break;
				case "only":
					exports.empty(refNode);
					refNode.appendChild(node);
					break;
				case "first":
					if(refNode.firstChild){
						_insertBefore(node, refNode.firstChild);
						break;
					}
					
				default: 
					refNode.appendChild(node);
			}
		}
		return node; 
	};

	exports.create = function create(/*DOMNode|String*/tag, /*Object*/attrs, /*DOMNode?|String?*/refNode, /*String?*/pos){
		var doc = win.doc;
		if(refNode){
			refNode = dom.byId(refNode);
			doc = refNode.ownerDocument;
		}
		if(typeof tag == "string"){ 
			tag = doc.createElement(tag);
		}
		if(attrs){ attr.set(tag, attrs); }
		if(refNode){ exports.place(tag, refNode, pos); }
		return tag; 
	};

	exports.empty =
				has("ie") ? function(node){
			node = dom.byId(node);
			for(var c; c = node.lastChild;){ 
				exports.destroy(c);
			}
		} :
				function(node){
			dom.byId(node).innerHTML = "";
		};

	exports.destroy = function destroy(/*DOMNode|String*/node){
		node = dom.byId(node);
		try{
			var doc = node.ownerDocument;
			
			if(!_destroyContainer || _destroyDoc != doc){
				_destroyContainer = doc.createElement("div");
				_destroyDoc = doc;
			}
			_destroyContainer.appendChild(node.parentNode ? node.parentNode.removeChild(node) : node);
			// NOTE: see http:
			_destroyContainer.innerHTML = "";
		}catch(e){
			/* squelch */
		}
	};
});

},
'dojo/keys':function(){
define("dojo/keys", ["./_base/kernel", "./_base/sniff"], function(dojo, has) {
	
	
	
	





return dojo.keys = {
	
	
	BACKSPACE: 8,
	TAB: 9,
	CLEAR: 12,
	ENTER: 13,
	SHIFT: 16,
	CTRL: 17,
	ALT: 18,
	META: has("safari") ? 91 : 224,		
	PAUSE: 19,
	CAPS_LOCK: 20,
	ESCAPE: 27,
	SPACE: 32,
	PAGE_UP: 33,
	PAGE_DOWN: 34,
	END: 35,
	HOME: 36,
	LEFT_ARROW: 37,
	UP_ARROW: 38,
	RIGHT_ARROW: 39,
	DOWN_ARROW: 40,
	INSERT: 45,
	DELETE: 46,
	HELP: 47,
	LEFT_WINDOW: 91,
	RIGHT_WINDOW: 92,
	SELECT: 93,
	NUMPAD_0: 96,
	NUMPAD_1: 97,
	NUMPAD_2: 98,
	NUMPAD_3: 99,
	NUMPAD_4: 100,
	NUMPAD_5: 101,
	NUMPAD_6: 102,
	NUMPAD_7: 103,
	NUMPAD_8: 104,
	NUMPAD_9: 105,
	NUMPAD_MULTIPLY: 106,
	NUMPAD_PLUS: 107,
	NUMPAD_ENTER: 108,
	NUMPAD_MINUS: 109,
	NUMPAD_PERIOD: 110,
	NUMPAD_DIVIDE: 111,
	F1: 112,
	F2: 113,
	F3: 114,
	F4: 115,
	F5: 116,
	F6: 117,
	F7: 118,
	F8: 119,
	F9: 120,
	F10: 121,
	F11: 122,
	F12: 123,
	F13: 124,
	F14: 125,
	F15: 126,
	NUM_LOCK: 144,
	SCROLL_LOCK: 145,
	UP_DPAD: 175,
	DOWN_DPAD: 176,
	LEFT_DPAD: 177,
	RIGHT_DPAD: 178,
	
	copyKey: has("mac") && !has("air") ? (has("safari") ? 91 : 224 ) : 17
};
});

},
'dojo/domReady':function(){
define(['./has'], function(has){
	var global = this,
		doc = document,
		readyStates = { 'loaded': 1, 'complete': 1 },
		fixReadyState = typeof doc.readyState != "string",
		ready = !!readyStates[doc.readyState];

	
	if(fixReadyState){ doc.readyState = "loading"; }

	if(!ready){
		var readyQ = [], tests = [],
			detectReady = function(evt){
				evt = evt || global.event;
				if(ready || (evt.type == "readystatechange" && !readyStates[doc.readyState])){ return; }
				ready = 1;

				
				if(fixReadyState){ doc.readyState = "complete"; }

				while(readyQ.length){
					(readyQ.shift())();
				}
			},
			on = function(node, event){
				node.addEventListener(event, detectReady, false);
				readyQ.push(function(){ node.removeEventListener(event, detectReady, false); });
			};

		if(!has("dom-addeventlistener")){
			on = function(node, event){
				event = "on" + event;
				node.attachEvent(event, detectReady);
				readyQ.push(function(){ node.detachEvent(event, detectReady); });
			};

			var div = doc.createElement("div");
			try{
				if(div.doScroll && global.frameElement === null){
					
					tests.push(function(){
						
						// http:
						try{
							div.doScroll("left");
							return 1;
						}catch(e){}
					});
				}
			}catch(e){}
		}

		on(doc, "DOMContentLoaded");
		on(global, "load");

		if("onreadystatechange" in doc){
			on(doc, "readystatechange");
		}else if(!fixReadyState){
			
			
			
			tests.push(function(){
				return readyStates[doc.readyState];
			});
		}

		if(tests.length){
			var poller = function(){
				if(ready){ return; }
				var i = tests.length;
				while(i--){
					if(tests[i]()){
						detectReady("poller");
						return;
					}
				}
				setTimeout(poller, 30);
			};
			poller();
		}
	}

	function domReady(callback){
		if(ready){
			callback(1);
		}else{
			readyQ.push(callback);
		}
	}
	domReady.load = function(id, req, load){
		domReady(load);
	};

	return domReady;
});

},
'dojo/_base/lang':function(){
define(["./kernel", "../has", "./sniff"], function(dojo, has){
	
	
	
	

	has.add("bug-for-in-skips-shadowed", function(){
		
		for(var i in {toString: 1}){
			return 0;
		}
		return 1;
	});

	var _extraNames =
			has("bug-for-in-skips-shadowed") ?
				"hasOwnProperty.valueOf.isPrototypeOf.propertyIsEnumerable.toLocaleString.toString.constructor".split(".") : [],

		_extraLen = _extraNames.length,

		_mixin = function(dest, source, copyFunc){
			var name, s, i, empty = {};
			for(name in source){
				
				
				
				s = source[name];
				if(!(name in dest) || (dest[name] !== s && (!(name in empty) || empty[name] !== s))){
					dest[name] = copyFunc ? copyFunc(s) : s;
				}
			}

			if(has("bug-for-in-skips-shadowed")){
				if(source){
					for(i = 0; i < _extraLen; ++i){
						name = _extraNames[i];
						s = source[name];
						if(!(name in dest) || (dest[name] !== s && (!(name in empty) || empty[name] !== s))){
							dest[name] = copyFunc ? copyFunc(s) : s;
						}
					}
				}
			}

			return dest; 
		},

		mixin = function(dest, sources){
			if(!dest){ dest = {}; }
			for(var i = 1, l = arguments.length; i < l; i++){
				lang._mixin(dest, arguments[i]);
			}
			return dest; 
		},

		getProp = function(/*Array*/parts, /*Boolean*/create, /*Object*/context){
			var p, i = 0, dojoGlobal = dojo.global;
			if(!context){
				if(!parts.length){
					return dojoGlobal;
				}else{
					p = parts[i++];
					try{
						context = dojo.scopeMap[p] && dojo.scopeMap[p][1];
					}catch(e){}
					context = context || (p in dojoGlobal ? dojoGlobal[p] : (create ? dojoGlobal[p] = {} : undefined));
				}
			}
			while(context && (p = parts[i++])){
				context = (p in context ? context[p] : (create ? context[p] = {} : undefined));
			}
			return context; 
		},

		setObject = function(name, value, context){
			var parts = name.split("."), p = parts.pop(), obj = getProp(parts, true, context);
			return obj && p ? (obj[p] = value) : undefined; 
		},

		getObject = function(name, create, context){
			return getProp(name.split("."), create, context); 
		},

		exists = function(name, obj){
			return lang.getObject(name, false, obj) !== undefined; 
		},

		opts = Object.prototype.toString,

		

		isString = function(it){
			return (typeof it == "string" || it instanceof String); 
		},

		isArray = function(it){
			return it && (it instanceof Array || typeof it == "array"); 
		},

		isFunction = function(it){
			return opts.call(it) === "[object Function]";
		},

		isObject = function(it){
			return it !== undefined &&
				(it === null || typeof it == "object" || lang.isArray(it) || lang.isFunction(it)); 
		},

		isArrayLike = function(it){
			return it && it !== undefined && 
				
				
				!lang.isString(it) && !lang.isFunction(it) &&
				!(it.tagName && it.tagName.toLowerCase() == 'form') &&
				(lang.isArray(it) || isFinite(it.length));
		},

		isAlien = function(it){
			return it && !lang.isFunction(it) && /\{\s*\[native code\]\s*\}/.test(String(it)); 
		},

		extend = function(constructor, props){
			for(var i=1, l=arguments.length; i<l; i++){
				lang._mixin(constructor.prototype, arguments[i]);
			}
			return constructor; 
		},

		_hitchArgs = function(scope, method){
			var pre = _toArray(arguments, 2);
			var named = lang.isString(method);
			return function(){
				
				var args = _toArray(arguments);
				
				var f = named ? (scope||dojo.global)[method] : method;
				
				return f && f.apply(scope || this, pre.concat(args)); 
			}; 
		},

		hitch = function(scope, method){
			if(arguments.length > 2){
				return lang._hitchArgs.apply(dojo, arguments); 
			}
			if(!method){
				method = scope;
				scope = null;
			}
			if(lang.isString(method)){
				scope = scope || dojo.global;
				if(!scope[method]){ throw(['dojo.hitch: scope["', method, '"] is null (scope="', scope, '")'].join('')); }
				return function(){ return scope[method].apply(scope, arguments || []); }; 
			}
			return !scope ? method : function(){ return method.apply(scope, arguments || []); }; 
		},

		delegate = (function(){
			
			function TMP(){}
			return function(obj, props){
				TMP.prototype = obj;
				var tmp = new TMP();
				TMP.prototype = null;
				if(props){
					lang._mixin(tmp, props);
				}
				return tmp; 
			};
		})(),

		efficient = function(obj, offset, startWith){
			return (startWith||[]).concat(Array.prototype.slice.call(obj, offset||0));
		},

		_toArray =
			has("ie") ?
				(function(){
					function slow(obj, offset, startWith){
						var arr = startWith||[];
						for(var x = offset || 0; x < obj.length; x++){
							arr.push(obj[x]);
						}
						return arr;
					}
					return function(obj){
						return ((obj.item) ? slow : efficient).apply(this, arguments);
					};
				})() : efficient,

		partial = function(/*Function|String*/method /*, ...*/){
			var arr = [ null ];
			return lang.hitch.apply(dojo, arr.concat(lang._toArray(arguments))); 
		},

		clone = function(/*anything*/ src){
			if(!src || typeof src != "object" || lang.isFunction(src)){
				
				return src;	
			}
			if(src.nodeType && "cloneNode" in src){
				
				return src.cloneNode(true); 
			}
			if(src instanceof Date){
				
				return new Date(src.getTime());	
			}
			if(src instanceof RegExp){
				
				return new RegExp(src);   
			}
			var r, i, l;
			if(lang.isArray(src)){
				
				r = [];
				for(i = 0, l = src.length; i < l; ++i){
					if(i in src){
						r.push(clone(src[i]));
					}
				}
	
	
	//			
	
			}else{
				
				r = src.constructor ? new src.constructor() : {};
			}
			return lang._mixin(r, src, clone);
		},


		trim = String.prototype.trim ?
			function(str){ return str.trim(); } :
			function(str){ return str.replace(/^\s\s*/, '').replace(/\s\s*$/, ''); },


		_pattern = /\{([^\}]+)\}/g,

		replace = function(tmpl, map, pattern){
			return tmpl.replace(pattern || _pattern, lang.isFunction(map) ?
				map : function(_, k){ return getObject(k, false, map); });
		},

		lang = {
			_extraNames:_extraNames,
			_mixin:_mixin,
			mixin:mixin,
			setObject:setObject,
			getObject:getObject,
			exists:exists,
			isString:isString,
			isArray:isArray,
			isFunction:isFunction,
			isObject:isObject,
			isArrayLike:isArrayLike,
			isAlien:isAlien,
			extend:extend,
			_hitchArgs:_hitchArgs,
			hitch:hitch,
			delegate:delegate,
			_toArray:_toArray,
			partial:partial,
			clone:clone,
			trim:trim,
			replace:replace
		};

	1 && mixin(dojo, lang);
	return lang;

	/*=====
	dojo._extraNames
	
	
	
	=====*/

	/*=====
	dojo._mixin = function(dest, source, copyFunc){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	=====*/

	/*=====
	dojo.mixin = function(dest, sources){
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//	|			
	
	
	
	//	|			
	
	
	//	|		
	
	
	//	| 
	
	
	
	
	
	
	
	
	
	
	
	
	
	//	| 
	
	//	| 
	
	}
	=====*/

	/*=====
	dojo.setObject = function(name, value, context){
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//	| 
	
	
	//	| 
	
	
	
	}
	=====*/

	/*=====
	dojo.getObject = function(name, create, context){
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	=====*/

	/*=====
	dojo.exists = function(name, obj){
	
	
	
	
	
	
	
	
	
	
	
	//	| 
	
	
	
	
	//	| 
	//	| lang.exists("foo.bar"); 
	//	| lang.exists("foo.bar.baz"); 
	
	//	| 
	//	| lang.exists("bar", foo); 
	//	| lang.exists("bar.baz", foo); 
	}
	=====*/

	/*=====
	dojo.isString = function(it){
	
	
	
	
	}
	=====*/

	/*=====
	dojo.isArray = function(it){
	
	
	
	
	
	}
	=====*/

	/*=====
	dojo.isFunction = function(it){
	
	
	
	
	}
	=====*/

	/*=====
	dojo.isObject = function(it){
	
	
	
	
	
	}
	=====*/

	/*=====
	dojo.isArrayLike = function(it){
	
	
	
	
	
	
	
	
	
	
	
	
	}
	=====*/

	/*=====
	dojo.isAlien = function(it){
	
	
	
	}
	=====*/

	/*=====
	dojo.extend = function(constructor, props){
	
	
	
	
	
	
	
	
	}
	=====*/

	/*=====
	dojo.hitch = function(scope, method){
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//	|	fn(3); 
	
	
	
	
	}
	=====*/

	/*=====
	dojo.delegate = function(obj, props){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	thinger.bar == "baz"; 
		//	|	foo.thud == undefined; 
		//	|	thinger.thud == "xyzzy"; 
		
		//	|	thinger.bar == "thonk"; 
	}
	=====*/

	/*=====
	dojo.partial = function(method){
	
	
	
	
	
	
	
	}
	=====*/

	/*=====
	dojo.trim = function(str){
		
		
		
		
		
		
		
		
		
		//		(see [Steven Levithan's blog](http:
		
		
		
	}
	=====*/

	/*=====
	dojo.clone = function(src){
	
	
	
	
	
	}
	=====*/

	/*=====
	dojo._toArray = function(obj, offset, startWith){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	=====*/

	/*=====
	dojo.replace = function(tmpl, map, pattern){
		
		
		
		
		
		
		
		
		
		//		string (see https:
		
		
		
		
		
		
		
		
		
		//	|	
		
		
		
		
		
		
		
		
		
		//	|	
		
		//	|	
		
		
		//	|	
		
		//	|	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
		//	|	
		
		
		//	|	
		return "";	
	}
	=====*/
});


},
'dojo/Evented':function(){
define(["./aspect", "./on"], function(aspect, on){
	
	
	
	
	
	
	
	
	
	
	
	

 	"use strict";
 	var after = aspect.after;
	function Evented(){
	}
	Evented.prototype = {
		on: function(type, listener){
			return on.parse(this, type, listener, function(target, type){
				return after(target, 'on' + type, listener, true);
			});
		},
		emit: function(type, event){
			var args = [this];
			args.push.apply(args, arguments);
			return on.emit.apply(on, args);
		}
	};
	return Evented;
});

},
'dojo/mouse':function(){
define(["./_base/kernel", "./on", "./has", "./dom", "./_base/window"], function(dojo, on, has, dom, win){

	/*=====
	dojo.mouse = {
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	};
	======*/

    has.add("dom-quirks", win.doc && win.doc.compatMode == "BackCompat");
 	has.add("events-mouseenter", win.doc && "onmouseenter" in win.doc.createElement("div"));

	var mouseButtons;
	if(has("dom-quirks") || !has("dom-addeventlistener")){
		mouseButtons = {
			LEFT:   1,
			MIDDLE: 4,
			RIGHT:  2,
			
			isButton: function(e, button){ return e.button & button; },
			isLeft:   function(e){ return e.button & 1; },
			isMiddle: function(e){ return e.button & 4; },
			isRight:  function(e){ return e.button & 2; }
		};
	}else{
		mouseButtons = {
			LEFT:   0,
			MIDDLE: 1,
			RIGHT:  2,
			
			isButton: function(e, button){ return e.button == button; },
			isLeft:   function(e){ return e.button == 0; },
			isMiddle: function(e){ return e.button == 1; },
			isRight:  function(e){ return e.button == 2; }
		};
	}
	dojo.mouseButtons = mouseButtons;

/*=====
	dojo.mouseButtons = {
		
		
		LEFT:   0,
		
		
		MIDDLE: 1,
		
		
		RIGHT:  2,

		isButton: function(e, button){
			
			
			
			
			
			
			return e.button == button; 
		},
		isLeft: function(e){
			
			
			
			
			return e.button == 0; 
		},
		isMiddle: function(e){
			
			
			
			
			return e.button == 1; 
		},
		isRight: function(e){
			
			
			
			
			return e.button == 2; 
		}
	};
=====*/

	function eventHandler(type, mustBubble){
		
		var handler = function(node, listener){
			return on(node, type, function(evt){
				if(!dom.isDescendant(evt.relatedTarget, mustBubble ? evt.target : node)){
					return listener.call(this, evt);
				}
			});
		};
		if(!mustBubble){
			handler.bubble = eventHandler(type, true);
		}
		return handler;
	}
	return {
		enter: eventHandler("mouseover"),
		leave: eventHandler("mouseout"),
		isLeft: mouseButtons.isLeft,
		isMiddle: mouseButtons.isMiddle,
		isRight: mouseButtons.isRight
	};
});

},
'dojo/topic':function(){
define(["./Evented"], function(Evented){
	
	
	
	
	
	
	

	var hub = new Evented;
	return {
		publish: function(topic, event){
			
			
			
			
			
			
			
			
			return hub.emit.apply(hub, arguments);
		},
		subscribe: function(topic, listener){
			
			
			
			
			
			
			return hub.on.apply(hub, arguments);
		}
	}
});

},
'dojo/_base/xhr':function(){
define([
	"./kernel", "./sniff", "require", "../io-query", "../dom", "../dom-form", "./Deferred", "./json", "./lang", "./array", "../on"
], function(dojo, has, require, ioq, dom, domForm, deferred, json, lang, array, on){
	
	
	
	

	has.add("native-xhr", function() {
		
		return typeof XMLHttpRequest !== 'undefined';
	});

	if(1){
		dojo._xhrObj = require.getXhr;
	}else if (has("native-xhr")){
		dojo._xhrObj = function(){
			
			
			try{
				return new XMLHttpRequest();
			}catch(e){
				throw new Error("XMLHTTP not available: "+e);
			}
		};
	}else{
		
		for(var XMLHTTP_PROGIDS = ['Msxml2.XMLHTTP', 'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.4.0'], progid, i = 0; i < 3;){
			try{
				progid = XMLHTTP_PROGIDS[i++];
				if (new ActiveXObject(progid)) {
					
					break;
				}
			}catch(e){
				
				
				
			}
		}
		dojo._xhrObj= function() {
			return new ActiveXObject(progid);
		};
	}

	var cfg = dojo.config;

	
	dojo.objectToQuery = ioq.objectToQuery;
	dojo.queryToObject = ioq.queryToObject;
	dojo.fieldToObject = domForm.fieldToObject;
	dojo.formToObject = domForm.toObject;
	dojo.formToQuery = domForm.toQuery;
	dojo.formToJson = domForm.toJson;

	
	
	

	dojo._blockAsync = false;

	
	var handlers = dojo._contentHandlers = dojo.contentHandlers = {
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|	
		
		
		
		
		

		"text": function(xhr){
			
			return xhr.responseText;
		},
		"json": function(xhr){
			
			return json.fromJson(xhr.responseText || null);
		},
		"json-comment-filtered": function(xhr){
			
			
			
			
			
			
			
			
			
			
			
			
			if(!dojo.config.useCommentedJson){
				console.warn("Consider using the standard mimetype:application/json."
					+ " json-commenting can introduce security issues. To"
					+ " decrease the chances of hijacking, use the standard the 'json' handler and"
					+ " prefix your json with: {}&&\n"
					+ "Use djConfig.useCommentedJson=true to turn off this message.");
			}

			var value = xhr.responseText;
			var cStartIdx = value.indexOf("\/*");
			var cEndIdx = value.lastIndexOf("*\/");
			if(cStartIdx == -1 || cEndIdx == -1){
				throw new Error("JSON was not comment filtered");
			}
			return json.fromJson(value.substring(cStartIdx+2, cEndIdx));
		},
		"javascript": function(xhr){
			

			
			return dojo.eval(xhr.responseText);
		},
		"xml": function(xhr){
			
			var result = xhr.responseXML;

			if(has("ie")){
				if((!result || !result.documentElement)){
					
					
					var ms = function(n){ return "MSXML" + n + ".DOMDocument"; };
					var dp = ["Microsoft.XMLDOM", ms(6), ms(4), ms(3), ms(2)];
					array.some(dp, function(p){
						try{
							var dom = new ActiveXObject(p);
							dom.async = false;
							dom.loadXML(xhr.responseText);
							result = dom;
						}catch(e){ return false; }
						return true;
					});
				}
		 }
			return result; 
		},
		"json-comment-optional": function(xhr){
			
			
			if(xhr.responseText && /^[^{\[]*\/\*/.test(xhr.responseText)){
				return handlers["json-comment-filtered"](xhr);
			}else{
				return handlers["json"](xhr);
			}
		}
	};

	/*=====
	dojo.__IoArgs = function(){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		this.url = url;
		this.content = content;
		this.timeout = timeout;
		this.form = form;
		this.preventCache = preventCache;
		this.handleAs = handleAs;
		this.ioPublish = ioPublish;
		this.load = function(response, ioArgs){
			
			
			
			
		}
		this.error = function(response, ioArgs){
			
			
			
			
		}
		this.handle = function(loadOrError, response, ioArgs){
			
			
			
			
			
			
			
		}
	}
	=====*/

	/*=====
	dojo.__IoCallbackArgs = function(args, xhr, url, query, handleAs, id, canDelete, json){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		this.args = args;
		this.xhr = xhr;
		this.url = url;
		this.query = query;
		this.handleAs = handleAs;
		this.id = id;
		this.canDelete = canDelete;
		this.json = json;
	}
	=====*/


	/*=====
	dojo.__IoPublish = function(){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		this.start = "/dojo/io/start";
		this.send = "/dojo/io/send";
		this.load = "/dojo/io/load";
		this.error = "/dojo/io/error";
		this.done = "/dojo/io/done";
		this.stop = "/dojo/io/stop";
	}
	=====*/


	dojo._ioSetArgs = function(/*dojo.__IoArgs*/args,
			/*Function*/canceller,
			/*Function*/okHandler,
			/*Function*/errHandler){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		var ioArgs = {args: args, url: args.url};

		
		var formObject = null;
		if(args.form){
			var form = dom.byId(args.form);
			
			
			var actnNode = form.getAttributeNode("action");
			ioArgs.url = ioArgs.url || (actnNode ? actnNode.value : null);
			formObject = domForm.toObject(form);
		}

		
		var miArgs = [{}];

		if(formObject){
			
			miArgs.push(formObject);
		}
		if(args.content){
			
			miArgs.push(args.content);
		}
		if(args.preventCache){
			miArgs.push({"dojo.preventCache": new Date().valueOf()});
		}
		ioArgs.query = ioq.objectToQuery(lang.mixin.apply(null, miArgs));

		
		ioArgs.handleAs = args.handleAs || "text";
		var d = new deferred(canceller);
		d.addCallbacks(okHandler, function(error){
			return errHandler(error, d);
		});

		
		
		
		
		var ld = args.load;
		if(ld && lang.isFunction(ld)){
			d.addCallback(function(value){
				return ld.call(args, value, ioArgs);
			});
		}
		var err = args.error;
		if(err && lang.isFunction(err)){
			d.addErrback(function(value){
				return err.call(args, value, ioArgs);
			});
		}
		var handle = args.handle;
		if(handle && lang.isFunction(handle)){
			d.addBoth(function(value){
				return handle.call(args, value, ioArgs);
			});
		}

		
		if(cfg.ioPublish && dojo.publish && ioArgs.args.ioPublish !== false){
			d.addCallbacks(
				function(res){
					dojo.publish("/dojo/io/load", [d, res]);
					return res;
				},
				function(res){
					dojo.publish("/dojo/io/error", [d, res]);
					return res;
				}
			);
			d.addBoth(function(res){
				dojo.publish("/dojo/io/done", [d, res]);
				return res;
			});
		}

		d.ioArgs = ioArgs;

		
		
		return d;
	};

	var _deferredCancel = function(/*Deferred*/dfd){
		

		dfd.canceled = true;
		var xhr = dfd.ioArgs.xhr;
		var _at = typeof xhr.abort;
		if(_at == "function" || _at == "object" || _at == "unknown"){
			xhr.abort();
		}
		var err = dfd.ioArgs.error;
		if(!err){
			err = new Error("xhr cancelled");
			err.dojoType="cancel";
		}
		return err;
	};
	var _deferredOk = function(/*Deferred*/dfd){
		

		var ret = handlers[dfd.ioArgs.handleAs](dfd.ioArgs.xhr);
		return ret === undefined ? null : ret;
	};
	var _deferError = function(/*Error*/error, /*Deferred*/dfd){
		

		if(!dfd.ioArgs.args.failOk){
			console.error(error);
		}
		return error;
	};

	
	
	var _inFlightIntvl = null;
	var _inFlight = [];


	
	
	
	
	
	var _pubCount = 0;
	var _checkPubCount = function(dfd){
		if(_pubCount <= 0){
			_pubCount = 0;
			if(cfg.ioPublish && dojo.publish && (!dfd || dfd && dfd.ioArgs.args.ioPublish !== false)){
				dojo.publish("/dojo/io/stop");
			}
		}
	};

	var _watchInFlight = function(){
		
		
		

		var now = (new Date()).getTime();
		
		
		
		if(!dojo._blockAsync){
			
			
			for(var i = 0, tif; i < _inFlight.length && (tif = _inFlight[i]); i++){
				var dfd = tif.dfd;
				var func = function(){
					if(!dfd || dfd.canceled || !tif.validCheck(dfd)){
						_inFlight.splice(i--, 1);
						_pubCount -= 1;
					}else if(tif.ioCheck(dfd)){
						_inFlight.splice(i--, 1);
						tif.resHandle(dfd);
						_pubCount -= 1;
					}else if(dfd.startTime){
						
						if(dfd.startTime + (dfd.ioArgs.args.timeout || 0) < now){
							_inFlight.splice(i--, 1);
							var err = new Error("timeout exceeded");
							err.dojoType = "timeout";
							dfd.errback(err);
							
							dfd.cancel();
							_pubCount -= 1;
						}
					}
				};
				if(dojo.config.debugAtAllCosts){
					func.call(this);
				}else{

						func.call(this);
	/*				}catch(e){
						dfd.errback(e);
					}*/
				}
			}
		}

		_checkPubCount(dfd);

		if(!_inFlight.length){
			clearInterval(_inFlightIntvl);
			_inFlightIntvl = null;
		}
	};

	dojo._ioCancelAll = function(){
		
		
		try{
			array.forEach(_inFlight, function(i){
				try{
					i.dfd.cancel();
				}catch(e){/*squelch*/}
			});
		}catch(e){/*squelch*/}
	};

	
	
	if(has("ie")){
		on(window, "unload", dojo._ioCancelAll);
	}

	dojo._ioNotifyStart = function(/*Deferred*/dfd){
		
		
		
		
		
		
		
		if(cfg.ioPublish && dojo.publish && dfd.ioArgs.args.ioPublish !== false){
			if(!_pubCount){
				dojo.publish("/dojo/io/start");
			}
			_pubCount += 1;
			dojo.publish("/dojo/io/send", [dfd]);
		}
	};

	dojo._ioWatch = function(dfd, validCheck, ioCheck, resHandle){
		
		
		
		
		
		
		
		
		
		
		
		
		
		var args = dfd.ioArgs.args;
		if(args.timeout){
			dfd.startTime = (new Date()).getTime();
		}

		_inFlight.push({dfd: dfd, validCheck: validCheck, ioCheck: ioCheck, resHandle: resHandle});
		if(!_inFlightIntvl){
			_inFlightIntvl = setInterval(_watchInFlight, 50);
		}
		
		
		
		
		
		if(args.sync){
			_watchInFlight();
		}
	};

	var _defaultContentType = "application/x-www-form-urlencoded";

	var _validCheck = function(/*Deferred*/dfd){
		return dfd.ioArgs.xhr.readyState; 
	};
	var _ioCheck = function(/*Deferred*/dfd){
		return 4 == dfd.ioArgs.xhr.readyState; 
	};
	var _resHandle = function(/*Deferred*/dfd){
		var xhr = dfd.ioArgs.xhr;
		if(dojo._isDocumentOk(xhr)){
			dfd.callback(dfd);
		}else{
			var err = new Error("Unable to load " + dfd.ioArgs.url + " status:" + xhr.status);
			err.status = xhr.status;
			err.responseText = xhr.responseText;
			err.xhr = xhr;
			dfd.errback(err);
		}
	};

	dojo._ioAddQueryToUrl = function(/*dojo.__IoCallbackArgs*/ioArgs){
		
		
		if(ioArgs.query.length){
			ioArgs.url += (ioArgs.url.indexOf("?") == -1 ? "?" : "&") + ioArgs.query;
			ioArgs.query = null;
		}
	};

	/*=====
	dojo.declare("dojo.__XhrArgs", dojo.__IoArgs, {
		constructor: function(){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			this.handleAs = handleAs;
			this.sync = sync;
			this.headers = headers;
			this.failOk = failOk;
		}
	});
	=====*/

	dojo.xhr = function(/*String*/ method, /*dojo.__XhrArgs*/ args, /*Boolean?*/ hasBody){
		
		
		
		
		
		
		
		
		
		
		

		
		var dfd = dojo._ioSetArgs(args, _deferredCancel, _deferredOk, _deferError);
		var ioArgs = dfd.ioArgs;

		
		
		var xhr = ioArgs.xhr = dojo._xhrObj(ioArgs.args);
		
		if(!xhr){
			dfd.cancel();
			return dfd;
		}

		
		if("postData" in args){
			ioArgs.query = args.postData;
		}else if("putData" in args){
			ioArgs.query = args.putData;
		}else if("rawBody" in args){
			ioArgs.query = args.rawBody;
		}else if((arguments.length > 2 && !hasBody) || "POST|PUT".indexOf(method.toUpperCase()) == -1){
			
			
			dojo._ioAddQueryToUrl(ioArgs);
		}

		
		
		xhr.open(method, ioArgs.url, args.sync !== true, args.user || undefined, args.password || undefined);
		if(args.headers){
			for(var hdr in args.headers){
				if(hdr.toLowerCase() === "content-type" && !args.contentType){
					args.contentType = args.headers[hdr];
				}else if(args.headers[hdr]){
					
					
					xhr.setRequestHeader(hdr, args.headers[hdr]);
				}
			}
		}
		
		if(args.contentType !== false){
			xhr.setRequestHeader("Content-Type", args.contentType || _defaultContentType);
		}
		if(!args.headers || !("X-Requested-With" in args.headers)){
			xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		}
		
		dojo._ioNotifyStart(dfd);
		if(dojo.config.debugAtAllCosts){
			xhr.send(ioArgs.query);
		}else{
			try{
				xhr.send(ioArgs.query);
			}catch(e){
				ioArgs.error = e;
				dfd.cancel();
			}
		}
		dojo._ioWatch(dfd, _validCheck, _ioCheck, _resHandle);
		xhr = null;
		return dfd; 
	};

	dojo.xhrGet = function(/*dojo.__XhrArgs*/ args){
		
		
		return dojo.xhr("GET", args); 
	};

	dojo.rawXhrPost = dojo.xhrPost = function(/*dojo.__XhrArgs*/ args){
		
		
		
		
		
		return dojo.xhr("POST", args, true); 
	};

	dojo.rawXhrPut = dojo.xhrPut = function(/*dojo.__XhrArgs*/ args){
		
		
		
		
		
		return dojo.xhr("PUT", args, true); 
	};

	dojo.xhrDelete = function(/*dojo.__XhrArgs*/ args){
		
		
		return dojo.xhr("DELETE", args); 
	};

	/*
	dojo.wrapForm = function(formNode){
		
		

		
		
		
		throw new Error("dojo.wrapForm not yet implemented");
	}
	*/

	dojo._isDocumentOk = function(http){
		var stat = http.status || 0;
		stat =
			(stat >= 200 && stat < 300) || 
			stat == 304 ||                 
			stat == 1223 ||                
			!stat;                         
		return stat; 
	};

	dojo._getText = function(url){
		var result;
		dojo.xhrGet({url:url, sync:true, load:function(text){
			result = text;
		}});
		return result;
	};

	
	lang.mixin(dojo.xhr, {
		_xhrObj: dojo._xhrObj,
		fieldToObject: domForm.fieldToObject,
		formToObject: domForm.toObject,
		objectToQuery: ioq.objectToQuery,
		formToQuery: domForm.toQuery,
		formToJson: domForm.toJson,
		queryToObject: ioq.queryToObject,
		contentHandlers: handlers,
		_ioSetArgs: dojo._ioSetArgs,
		_ioCancelAll: dojo._ioCancelAll,
		_ioNotifyStart: dojo._ioNotifyStart,
		_ioWatch: dojo._ioWatch,
		_ioAddQueryToUrl: dojo._ioAddQueryToUrl,
		_isDocumentOk: dojo._isDocumentOk,
		_getText: dojo._getText,
		get: dojo.xhrGet,
		post: dojo.xhrPost,
		put: dojo.xhrPut,
		del: dojo.xhrDelete	
	});

	return dojo.xhr;
});

},
'dojo/_base/unload':function(){
define(["./kernel", "./connect"], function(dojo, connect) {
	
	
	
	

	var win = window;

	/*=====
		dojo.windowUnloaded = function(){
			
			
			
			
			
			
			
			
			
		};
	=====*/

	dojo.addOnWindowUnload = function(/*Object?|Function?*/obj, /*String|Function?*/functionName){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		if (!dojo.windowUnloaded) {
			connect.connect(win, "unload", (dojo.windowUnloaded= function(){}));
		}
		connect.connect(win, "unload", obj, functionName);
	};

	dojo.addOnUnload = function(/*Object?|Function?*/obj, /*String|Function?*/functionName){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		connect.connect(win, "beforeunload", obj, functionName);
	};

	return {
		addOnWindowUnload: dojo.addOnWindowUnload,
		addOnUnload: dojo.addOnUnload
	};
});

},
'dojo/_base/NodeList':function(){
define(["./kernel", "../query", "./array", "./html", "../NodeList-dom"], function(dojo, query, array){
  
  
  
  
 
var NodeList = query.NodeList;

	/*=====
	dojo.extend(dojo.NodeList, {
		connect: function(methodName, objOrFunc, funcName){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		},
		coords: function(){
			
			
			
			
			
			

			return dojo.map(this, dojo.coords); 
		}
	 });

	 var NodeList = dojo.NodeList;
	=====*/
	var nlp = NodeList.prototype;

	
	nlp.connect = NodeList._adaptAsForEach(function(){
		return dojo.connect.apply(this, arguments);
	});
	nlp.coords = NodeList._adaptAsMap(dojo.coords);

	NodeList.events = [
		
		
		"blur", "focus", "change", "click", "error", "keydown", "keypress",
		"keyup", "load", "mousedown", "mouseenter", "mouseleave", "mousemove",
		"mouseout", "mouseover", "mouseup", "submit"
	];

	

	
	array.forEach(NodeList.events, function(evt){
			var _oe = "on" + evt;
			nlp[_oe] = function(a, b){
				return this.connect(_oe, a, b);
			};
				
				/*
				return (a ? this.connect(_oe, a, b) :
							this.forEach(function(n){
								
								
								
								
								
								//		http:

								console.log(n, evt, _oe);

								
								var _e = { target: n, faux: true, type: evt };
								
								try{ n[evt](_e); }catch(e){ console.log(e); }
								try{ n[_oe](_e); }catch(e){ console.log(e); }
							})
				);
				*/
		}
	);

	dojo.NodeList = NodeList;
	return dojo.NodeList;
});

},
'dojo/_base/Color':function(){
define(["./kernel", "./lang", "./array", "./config"], function(dojo, lang, ArrayUtil, config){

	var Color = dojo.Color = function(/*Array|String|Object*/ color){
		
		
		
		
		
		
		
		
		//	 | c.setColor([0,0,0]); 
		//	 | var hex = c.toHex(); 
		
		
		
		
		
		//	 | 
		
		//	 | console.log(n.toString()); 
		if(color){ this.setColor(color); }
	};

	/*=====
	lang.mixin(dojo.Color,{
		named:{
			
		}
	});
	=====*/

	
	
	
	Color.named = {
		"black":  [0,0,0],
		"silver": [192,192,192],
		"gray":	  [128,128,128],
		"white":  [255,255,255],
		"maroon": [128,0,0],
		"red":	  [255,0,0],
		"purple": [128,0,128],
		"fuchsia":[255,0,255],
		"green":  [0,128,0],
		"lime":	  [0,255,0],
		"olive":  [128,128,0],
		"yellow": [255,255,0],
		"navy":	  [0,0,128],
		"blue":	  [0,0,255],
		"teal":	  [0,128,128],
		"aqua":	  [0,255,255],
		"transparent": config.transparentColor || [0,0,0,0]
	};

	lang.extend(Color, {
		r: 255, g: 255, b: 255, a: 1,
		_set: function(r, g, b, a){
			var t = this; t.r = r; t.g = g; t.b = b; t.a = a;
		},
		setColor: function(/*Array|String|Object*/ color){
			
			
			
			
			
			
			//	|	var c = new dojo.Color(); 
			//	|	c.setColor("#ededed"); 
			if(lang.isString(color)){
				Color.fromString(color, this);
			}else if(lang.isArray(color)){
				Color.fromArray(color, this);
			}else{
				this._set(color.r, color.g, color.b, color.a);
				if(!(color instanceof Color)){ this.sanitize(); }
			}
			return this;	
		},
		sanitize: function(){
			
			
			
			
			
			return this;	
		},
		toRgb: function(){
			
			
			
			
			//	|	console.log(c.toRgb()); 
			var t = this;
			return [t.r, t.g, t.b]; 
		},
		toRgba: function(){
			
			
			
			var t = this;
			return [t.r, t.g, t.b, t.a];	
		},
		toHex: function(){
			
			
			
			//	|	console.log(new dojo.Color([0,0,0]).toHex()); 
			var arr = ArrayUtil.map(["r", "g", "b"], function(x){
				var s = this[x].toString(16);
				return s.length < 2 ? "0" + s : s;
			}, this);
			return "#" + arr.join("");	
		},
		toCss: function(/*Boolean?*/ includeAlpha){
			
			
			
			
			//	|	console.log(c); 
			var t = this, rgb = t.r + ", " + t.g + ", " + t.b;
			return (includeAlpha ? "rgba(" + rgb + ", " + t.a : "rgb(" + rgb) + ")";	
		},
		toString: function(){
			
			
			return this.toCss(true); 
		}
	});

	Color.blendColors = dojo.blendColors = function(
		/*dojo.Color*/ start,
		/*dojo.Color*/ end,
		/*Number*/ weight,
		/*dojo.Color?*/ obj
	){
		
		
		
		var t = obj || new Color();
		ArrayUtil.forEach(["r", "g", "b", "a"], function(x){
			t[x] = start[x] + (end[x] - start[x]) * weight;
			if(x != "a"){ t[x] = Math.round(t[x]); }
		});
		return t.sanitize();	
	};

	Color.fromRgb = dojo.colorFromRgb = function(/*String*/ color, /*dojo.Color?*/ obj){
		
		
		
		
		
		
		
		var m = color.toLowerCase().match(/^rgba?\(([\s\.,0-9]+)\)/);
		return m && Color.fromArray(m[1].split(/\s*,\s*/), obj);	
	};

	Color.fromHex = dojo.colorFromHex = function(/*String*/ color, /*dojo.Color?*/ obj){
		
		
		
		
		
		
		
		
		
		//	 | var thing = dojo.colorFromHex("#ededed"); 
		
		
		//	| var thing = dojo.colorFromHex("#000"); 
		var t = obj || new Color(),
			bits = (color.length == 4) ? 4 : 8,
			mask = (1 << bits) - 1;
		color = Number("0x" + color.substr(1));
		if(isNaN(color)){
			return null; 
		}
		ArrayUtil.forEach(["b", "g", "r"], function(x){
			var c = color & mask;
			color >>= bits;
			t[x] = bits == 4 ? 17 * c : c;
		});
		t.a = 1;
		return t;	
	};

	Color.fromArray = dojo.colorFromArray = function(/*Array*/ a, /*dojo.Color?*/ obj){
		
		
		
		
		//		| var myColor = dojo.colorFromArray([237,237,237,0.5]); 
		
		
		var t = obj || new Color();
		t._set(Number(a[0]), Number(a[1]), Number(a[2]), Number(a[3]));
		if(isNaN(t.a)){ t.a = 1; }
		return t.sanitize();	
	};

	Color.fromString = dojo.colorFromString = function(/*String*/ str, /*dojo.Color?*/ obj){
		
		
		
		
		
		
		
		
		
		
		var a = Color.named[str];
		return a && Color.fromArray(a, obj) || Color.fromRgb(str, obj) || Color.fromHex(str, obj);	
	};

	return Color;
});

},
'dojo/selector/_loader':function(){
define(["../has", "require"],
		function(has, require){


"use strict";
var testDiv = document.createElement("div");
has.add("dom-qsa2.1", !!testDiv.querySelectorAll);
has.add("dom-qsa3", function(){
			
			try{
				testDiv.innerHTML = "<p class='TEST'></p>"; 
				
				
				return testDiv.querySelectorAll(".TEST:empty").length == 1;
			}catch(e){}
		});
var fullEngine;
var acme = "./acme", lite = "./lite";
return {
	load: function(id, parentRequire, loaded, config){
		var req = require;
		
		id = id == "default" ? has("config-selectorEngine") || "css3" : id;
		id = id == "css2" || id == "lite" ? lite :
				id == "css2.1" ? has("dom-qsa2.1") ? lite : acme :
				id == "css3" ? has("dom-qsa3") ? lite : acme :
				id == "acme" ? acme : (req = parentRequire) && id;
		if(id.charAt(id.length-1) == '?'){
			id = id.substring(0,id.length - 1);
			var optionalLoad = true;
		}
		
		if(optionalLoad && (has("dom-compliant-qsa") || fullEngine)){
			return loaded(fullEngine);
		}
		
		req([id], function(engine){
			if(id != "./lite"){
				fullEngine = engine;
			}
			loaded(engine);
		});
	}
};
});

},
'dojo/on':function(){
define(["./has!dom-addeventlistener?:./aspect", "./_base/kernel", "./has"], function(aspect, dojo, has){
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

 	"use strict";
	if(1){ 
		var major = window.ScriptEngineMajorVersion;
		has.add("jscript", major && (major() + ScriptEngineMinorVersion() / 10));
		has.add("event-orientationchange", has("touch") && !has("android")); 
	}
	var on = function(target, type, listener, dontFix){
		if(target.on){ 
			
			return target.on(type, listener);
		}
		
		return on.parse(target, type, listener, addListener, dontFix, this);
	};
	on.pausable =  function(target, type, listener, dontFix){
		
		
		
		
		
		var paused;
		var signal = on(target, type, function(){
			if(!paused){
				return listener.apply(this, arguments);
			}
		}, dontFix);
		signal.pause = function(){
			paused = true;
		};
		signal.resume = function(){
			paused = false;
		};
		return signal;
	};
	on.once = function(target, type, listener, dontFix){
		
		
		
		
		var signal = on(target, type, function(){
			
			signal.remove();
			
			return listener.apply(this, arguments);
		});
		return signal;
	};
	on.parse = function(target, type, listener, addListener, dontFix, matchesTarget){
		if(type.call){
			
			
			return type.call(matchesTarget, target, listener);
		}

		if(type.indexOf(",") > -1){
			
			var events = type.split(/\s*,\s*/);
			var handles = [];
			var i = 0;
			var eventName;
			while(eventName = events[i++]){
				handles.push(addListener(target, eventName, listener, dontFix, matchesTarget));
			}
			handles.remove = function(){
				for(var i = 0; i < handles.length; i++){
					handles[i].remove();
				}
			};
			return handles;
		}
		return addListener(target, type, listener, dontFix, matchesTarget)
	};
	var touchEvents = /^touch/;
	function addListener(target, type, listener, dontFix, matchesTarget){		
		
		var selector = type.match(/(.*):(.*)/);
		
		if(selector){
			type = selector[2];
			selector = selector[1];
			
			return on.selector(selector, type).call(matchesTarget, target, listener);
		}
		
		if(has("touch")){
			if(touchEvents.test(type)){
				
				listener = fixTouchListener(listener);
			}
			if(!has("event-orientationchange") && (type == "orientationchange")){
				
				
				type = "resize"; 
				target = window;
				listener = fixTouchListener(listener);
			} 
		}
		
		if(target.addEventListener){
			
			
			var capture = type in captures;
			target.addEventListener(capture ? captures[type] : type, listener, capture);
			
			return {
				remove: function(){
					target.removeEventListener(type, listener, capture);
				}
			};
		}
		type = "on" + type;
		if(fixAttach && target.attachEvent){
			return fixAttach(target, type, listener);
		}
	 	throw new Error("Target must be an event emitter");
	}

	on.selector = function(selector, eventType, children){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return function(target, listener){
			var matchesTarget = this;
			var bubble = eventType.bubble;
			if(bubble){
				
				eventType = bubble;
			}else if(children !== false){
				
				children = true;
			}
			return on(target, eventType, function(event){
				var eventTarget = event.target;
				
				matchesTarget = matchesTarget && matchesTarget.matches ? matchesTarget : dojo.query;
				
				while(!matchesTarget.matches(eventTarget, selector, target)){
					if(eventTarget == target || !children || !(eventTarget = eventTarget.parentNode)){ 
						return;
					}
				}
				return listener.call(eventTarget, event);
			});
		};
	};

	function syntheticPreventDefault(){
		this.cancelable = false;
	}
	function syntheticStopPropagation(){
		this.bubbles = false;
	}
	var slice = [].slice,
		syntheticDispatch = on.emit = function(target, type, event){
		
		
		
		
		
		
		
		
		
		
		//		An object that provides the properties for the event. See https:
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		var args = slice.call(arguments, 2);
		var method = "on" + type;
		if("parentNode" in target){
			
			var newEvent = args[0] = {};
			for(var i in event){
				newEvent[i] = event[i];
			}
			newEvent.preventDefault = syntheticPreventDefault;
			newEvent.stopPropagation = syntheticStopPropagation;
			newEvent.target = target;
			newEvent.type = type;
			event = newEvent;
		}
		do{
			
			target[method] && target[method].apply(target, args);
			
		}while(event && event.bubbles && (target = target.parentNode));
		return event && event.cancelable && event; 
	};
	var captures = {}; 
	if(has("dom-addeventlistener")){
		
		captures = {
			focusin: "focus",
			focusout: "blur"
		};
		if(has("opera")){
			captures.keydown = "keypress"; 
		}

		
		on.emit = function(target, type, event){
			if(target.dispatchEvent && document.createEvent){
				
				
				
				
				
				
				var nativeEvent = document.createEvent("HTMLEvents");
				nativeEvent.initEvent(type, !!event.bubbles, !!event.cancelable);
				
				for(var i in event){
					var value = event[i];
					if(!(i in nativeEvent)){
						nativeEvent[i] = event[i];
					}
				}
				return target.dispatchEvent(nativeEvent) && nativeEvent;
			}
			return syntheticDispatch.apply(on, arguments); 
		};
	}else{
		
		on._fixEvent = function(evt, sender){
			
			
			
			
			
			
			
			if(!evt){
				var w = sender && (sender.ownerDocument || sender.document || sender).parentWindow || window;
				evt = w.event;
			}
			if(!evt){return(evt);}
			if(!evt.target){ 
				evt.target = evt.srcElement;
				evt.currentTarget = (sender || evt.srcElement);
				if(evt.type == "mouseover"){
					evt.relatedTarget = evt.fromElement;
				}
				if(evt.type == "mouseout"){
					evt.relatedTarget = evt.toElement;
				}
				if(!evt.stopPropagation){
					evt.stopPropagation = stopPropagation;
					evt.preventDefault = preventDefault;
				}
				switch(evt.type){
					case "keypress":
						var c = ("charCode" in evt ? evt.charCode : evt.keyCode);
						if (c==10){
							
							c=0;
							evt.keyCode = 13;
						}else if(c==13||c==27){
							c=0; 
						}else if(c==3){
							c=99; 
						}
						
						
						evt.charCode = c;
						_setKeyChar(evt);
						break;
				}
			}
			return evt;
		};
		var IESignal = function(handle){
			this.handle = handle;
		};
		IESignal.prototype.remove = function(){
			delete _dojoIEListeners_[this.handle];
		};
		var fixListener = function(listener){
			
			return function(evt){
				evt = on._fixEvent(evt, this);
				return listener.call(this, evt);
			}
		}
		var fixAttach = function(target, type, listener){
			listener = fixListener(listener);
			if(((target.ownerDocument ? target.ownerDocument.parentWindow : target.parentWindow || target.window || window) != top || 
						has("jscript") < 5.8) && 
					!has("config-_allow_leaks")){
				
				
				if(typeof _dojoIEListeners_ == "undefined"){
					_dojoIEListeners_ = [];
				}
				var emiter = target[type];
				if(!emiter || !emiter.listeners){
					var oldListener = emiter;
					target[type] = emiter = Function('event', 'var callee = arguments.callee; for(var i = 0; i<callee.listeners.length; i++){var listener = _dojoIEListeners_[callee.listeners[i]]; if(listener){listener.call(this,event);}}');
					emiter.listeners = [];
					emiter.global = this;
					if(oldListener){
						emiter.listeners.push(_dojoIEListeners_.push(oldListener) - 1);
					}
				}
				var handle;
				emiter.listeners.push(handle = (emiter.global._dojoIEListeners_.push(listener) - 1));
				return new IESignal(handle);
			}
			return aspect.after(target, type, listener, true);
		};

		var _setKeyChar = function(evt){
			evt.keyChar = evt.charCode ? String.fromCharCode(evt.charCode) : '';
			evt.charOrCode = evt.keyChar || evt.keyCode;
		};
		
		var stopPropagation = function(){
			this.cancelBubble = true;
		};
		var preventDefault = on._preventDefault = function(){
			
			
			
			
			
			
			this.bubbledKeyCode = this.keyCode;
			if(this.ctrlKey){
				try{
					
					
					this.keyCode = 0;
				}catch(e){
				}
			}
			this.returnValue = false;
		};
	}
	if(has("touch")){ 
		var Event = function (){};
		var windowOrientation = window.orientation; 
		var fixTouchListener = function(listener){ 
			return function(originalEvent){ 
				
				
				
				

				
				var event = originalEvent.corrected;
				if(!event){
					var type = originalEvent.type;
					try{
						delete originalEvent.type; 
					}catch(e){} 
					if(originalEvent.type){
						
						Event.prototype = originalEvent;
						var event = new Event;
						
						event.preventDefault = function(){
							originalEvent.preventDefault();
						};
						event.stopPropagation = function(){
							originalEvent.stopPropagation();
						};
					}else{
						
						event = originalEvent;
						event.type = type;
					}
					originalEvent.corrected = event;
					if(type == 'resize'){
						if(windowOrientation == window.orientation){ 
							return null;
						} 
						windowOrientation = window.orientation;
						event.type = "orientationchange"; 
						return listener.call(this, event);
					}
					
					if(!("rotation" in event)){ 
						event.rotation = 0; 
						event.scale = 1;
					}
					
					var firstChangeTouch = event.changedTouches[0];
					for(var i in firstChangeTouch){ 
						delete event[i]; 
						event[i] = firstChangeTouch[i];
					}
				}
				return listener.call(this, event); 
			}; 
		}; 
	}
	return on;
});

},
'dojo/_base/sniff':function(){
define(["./kernel", "../has"], function(dojo, has){
	
	
	
	

	if(!1){
		return has;
	}

	dojo.isBrowser = true,
	dojo._name = "browser";

	var hasAdd = has.add,
		n = navigator,
		dua = n.userAgent,
		dav = n.appVersion,
		tv = parseFloat(dav),
		isOpera,
		isAIR,
		isKhtml,
		isWebKit,
		isChrome,
		isMac,
		isSafari,
		isMozilla ,
		isMoz,
		isIE,
		isFF,
		isQuirks,
		isIos,
		isAndroid,
		isWii;

	/*=====
	dojo.isBrowser = {
		
		
	};

	dojo.isFF = {
		
		
	};

	dojo.isIE = {
		
		
		//	|		
		
	};

	dojo.isSafari = {
		
		
		
		
		
		//	|		
		
	};

	dojo.mixin(dojo, {
		
		
		isBrowser: true,
		
		
		
		isFF: 2,
		
		
		
		isIE: 6,
		
		
		
		isKhtml: 0,
		
		
		
		isWebKit: 0,
		
		
		
		isMozilla: 0,
		
		
		
		isOpera: 0,
		
		
		isSafari: 0,
		
		
		isChrome: 0,
		
		
		isMac: 0,
		
		
		isIos: 0,
		
		
		isAndroid: 0,
		
		
		isWii: 0
	});
	=====*/

	
	if(dua.indexOf("AdobeAIR") >= 0){ isAIR = 1; }
	isKhtml = (dav.indexOf("Konqueror") >= 0) ? tv : 0;
	isWebKit = parseFloat(dua.split("WebKit/")[1]) || undefined;
	isChrome = parseFloat(dua.split("Chrome/")[1]) || undefined;
	isMac = dav.indexOf("Macintosh") >= 0;
	isIos = /iPhone|iPod|iPad/.test(dua);
	isAndroid = parseFloat(dua.split("Android ")[1]) || undefined;
	isWii = typeof opera != "undefined" && opera.wiiremote;

	
	//		http:
	//		http:
	var index = Math.max(dav.indexOf("WebKit"), dav.indexOf("Safari"), 0);
	if(index && !isChrome){
		
		
		
		isSafari = parseFloat(dav.split("Version/")[1]);
		if(!isSafari || parseFloat(dav.substr(index + 7)) <= 419.3){
			isSafari = 2;
		}
	}

	if (!has("dojo-webkit")) {
		if(dua.indexOf("Opera") >= 0){
			isOpera = tv;
			// see http://dev.opera.com/articles/view/opera-ua-string-changes and http:
			
			if(isOpera >= 9.8){
				isOpera = parseFloat(dua.split("Version/")[1]) || tv;
			}
		}

		if(dua.indexOf("Gecko") >= 0 && !isKhtml && !isWebKit){
			isMozilla = isMoz = tv;
		}
		if(isMoz){
			
			isFF = parseFloat(dua.split("Firefox/")[1] || dua.split("Minefield/")[1]) || undefined;
		}
		if(document.all && !isOpera){
			isIE = parseFloat(dav.split("MSIE ")[1]) || undefined;
			
			
			
			
			
			
			var mode = document.documentMode;
			if(mode && mode != 5 && Math.floor(isIE) != mode){
				isIE = mode;
			}
		}
	}

	isQuirks = document.compatMode == "BackCompat";

	hasAdd("opera", dojo.isOpera = isOpera);
	hasAdd("air", dojo.isAIR = isAIR);
	hasAdd("khtml", dojo.isKhtml = isKhtml);
	hasAdd("webkit", dojo.isWebKit = isWebKit);
	hasAdd("chrome", dojo.isChrome = isChrome);
	hasAdd("mac", dojo.isMac = isMac );
	hasAdd("safari", dojo.isSafari = isSafari);
	hasAdd("mozilla", dojo.isMozilla = dojo.isMoz = isMozilla );
	hasAdd("ie", dojo.isIE = isIE );
	hasAdd("ff", dojo.isFF = isFF);
	hasAdd("quirks", dojo.isQuirks = isQuirks);
	hasAdd("ios", dojo.isIos = isIos);
	hasAdd("android", dojo.isAndroid = isAndroid);

	dojo.locale = dojo.locale || (isIE ? n.userLanguage : n.language).toLowerCase();

	return has;
});

},
'dojo/_base/array':function(){
define("dojo/_base/array", ["./kernel", "../has", "./lang"], function(dojo, has, lang){
	
	
	
	

	/*=====
	dojo.indexOf = function(arr, value, fromIndex, findLast){
		
		
		
		
		
		
		
		
		//			https:
		
		
		
		
		
	};
	dojo.lastIndexOf = function(arr, value, fromIndex){
		
		
		
		
		
		
		
		
		//			https:
		
		
		
		
	};
	dojo.forEach = function(arr, callback, thisObject){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//			https:
		
		//	| 
		
		
		
		
		
		
		
		//	| 
		
		
		
		
		
		
		
		//	| 
		
		
		
		
		
		
		
		
		//	| 
		
		
		
		
		
		
		//	| 
		
		
		
		
		
		
		
	};
	dojo.every = function(arr, callback, thisObject){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//			https:
		
		//	| 
		
		
		//	| 
		
	};
	dojo.some = function(arr, callback, thisObject){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//			https:
		
		//	| 
		
		
		//	| 
		
	};
	dojo.map = function(arr, callback, thisObject){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//			https:
		
		//	| 
		
	};
	dojo.filter = function(arr, callback, thisObject){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//			https:
		
		//	| 
		
	};
	=====*/

	
	var cache = {}, u, array; 

	function clearCache(){
		cache = {};
	}

	function buildFn(fn){
		return cache[fn] = new Function("item", "index", "array", fn); 
	}
	

	

	function everyOrSome(some){
		var every = !some;
		return function(a, fn, o){
			var i = 0, l = a && a.length || 0, result;
			if(l && typeof a == "string") a = a.split("");
			if(typeof fn == "string") fn = cache[fn] || buildFn(fn);
			if(o){
				for(; i < l; ++i){
					result = !fn.call(o, a[i], i, a);
					if(some ^ result){
						return !result;
					}
				}
			}else{
				for(; i < l; ++i){
					result = !fn(a[i], i, a);
					if(some ^ result){
						return !result;
					}
				}
			}
			return every; 
		}
	}
	

	

	function index(up){
		var delta = 1, lOver = 0, uOver = 0;
		if(!up){
			delta = lOver = uOver = -1;
		}
		return function(a, x, from, last){
			if(last && delta > 0){
				
				return array.lastIndexOf(a, x, from);
			}
			var l = a && a.length || 0, end = up ? l + uOver : lOver, i;
			if(from === u){
				i = up ? lOver : l + uOver;
			}else{
				if(from < 0){
					i = l + from;
					if(i < 0){
						i = lOver;
					}
				}else{
					i = from >= l ? l + uOver : from;
				}
			}
			if(l && typeof a == "string") a = a.split("");
			for(; i != end; i += delta){
				if(a[i] == x){
					return i; 
				}
			}
			return -1; 
		}
	}
	

	function forEach(a, fn, o){
		var i = 0, l = a && a.length || 0;
		if(l && typeof a == "string") a = a.split("");
		if(typeof fn == "string") fn = cache[fn] || buildFn(fn);
		if(o){
			for(; i < l; ++i){
				fn.call(o, a[i], i, a);
			}
		}else{
			for(; i < l; ++i){
				fn(a[i], i, a);
			}
		}
	}

	function map(a, fn, o, Ctr){
		
		var i = 0, l = a && a.length || 0, out = new (Ctr || Array)(l);
		if(l && typeof a == "string") a = a.split("");
		if(typeof fn == "string") fn = cache[fn] || buildFn(fn);
		if(o){
			for(; i < l; ++i){
				out[i] = fn.call(o, a[i], i, a);
			}
		}else{
			for(; i < l; ++i){
				out[i] = fn(a[i], i, a);
			}
		}
		return out; 
	}

	function filter(a, fn, o){
		
		var i = 0, l = a && a.length || 0, out = [], value;
		if(l && typeof a == "string") a = a.split("");
		if(typeof fn == "string") fn = cache[fn] || buildFn(fn);
		if(o){
			for(; i < l; ++i){
				value = a[i];
				if(fn.call(o, value, i, a)){
					out.push(value);
				}
			}
		}else{
			for(; i < l; ++i){
				value = a[i];
				if(fn(value, i, a)){
					out.push(value);
				}
			}
		}
		return out; 
	}

	array = {
		every:       everyOrSome(false),
		some:        everyOrSome(true),
		indexOf:     index(true),
		lastIndexOf: index(false),
		forEach:     forEach,
		map:         map,
		filter:      filter,
		clearCache:  clearCache
	};

	1 && lang.mixin(dojo, array);

	/*===== return dojo.array; =====*/
	return array;
});

},
'dojo/_base/json':function(){
define(["./kernel", "../json"], function(dojo, json){
  
  
  
  

dojo.fromJson = function(/*String*/ js){
	
	
	
	
	
	
	
	
	
	
	

	return eval("(" + js + ")"); 
};

/*=====
dojo._escapeString = function(){
	
	
	
	
};
=====*/
dojo._escapeString = json.stringify; 

dojo.toJsonIndentStr = "\t";
dojo.toJson = function(/*Object*/ it, /*Boolean?*/ prettyPrint){
	
	//		Returns a [JSON](http:
	
	//		Returns a [JSON](http:
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	return json.stringify(it, function(key, value){
		if(value){
			var tf = value.__json__||value.json;
			if(typeof tf == "function"){
				return tf.call(value);
			}
		}
		return value;
	}, prettyPrint && dojo.toJsonIndentStr);	
};

return dojo;
});

},
'dojo/dom-class':function(){
define(["./_base/lang", "./_base/array", "./dom"], function(lang, array, dom){
	
	
	
	

	var className = "className";

	/* Part I of classList-based implementation is preserved here for posterity
	var classList = "classList";
	has.add("dom-classList", function(){
		return classList in document.createElement("p");
	});
	*/

	
	
	

	/*=====
	dojo.hasClass = function(node, classStr){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo.addClass = function(node, classStr){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo.removeClass = function(node, classStr){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo.replaceClass = function(node, addClassStr, removeClassStr){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	/*=====
	dojo.toggleClass = function(node, classStr, condition){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	=====*/

	var cls, 
		spaces = /\s+/, a1 = [""];

	function str2array(s){
		if(typeof s == "string" || s instanceof String){
			if(s && !spaces.test(s)){
				a1[0] = s;
				return a1;
			}
			var a = s.split(spaces);
			if(a.length && !a[0]){
				a.shift();
			}
			if(a.length && !a[a.length - 1]){
				a.pop();
			}
			return a;
		}
		
		if(!s){
			return [];
		}
		return array.filter(s, function(x){ return x; });
	}

	/* Part II of classList-based implementation is preserved here for posterity
	if(has("dom-classList")){
		
		cls = {
			contains: function containsClass(node, classStr){
				var clslst = classStr && dom.byId(node)[classList];
				return clslst && clslst.contains(classStr); 
			},

			add: function addClass(node, classStr){
				node = dom.byId(node);
				classStr = str2array(classStr);
				for(var i = 0, len = classStr.length; i < len; ++i){
					node[classList].add(classStr[i]);
				}
			},

			remove: function removeClass(node, classStr){
				node = dom.byId(node);
				if(classStr === undefined){
					node[className] = "";
				}else{
					classStr = str2array(classStr);
					for(var i = 0, len = classStr.length; i < len; ++i){
						node[classList].remove(classStr[i]);
					}
				}
			},

			replace: function replaceClass(node, addClassStr, removeClassStr){
				node = dom.byId(node);
				if(removeClassStr === undefined){
					node[className] = "";
				}else{
					removeClassStr = str2array(removeClassStr);
					for(var i = 0, len = removeClassStr.length; i < len; ++i){
						node[classList].remove(removeClassStr[i]);
					}
				}
				addClassStr = str2array(addClassStr);
				for(i = 0, len = addClassStr.length; i < len; ++i){
					node[classList].add(addClassStr[i]);
				}
			},

			toggle: function toggleClass(node, classStr, condition){
				node = dom.byId(node);
				if(condition === undefined){
					classStr = str2array(classStr);
					for(var i = 0, len = classStr.length; i < len; ++i){
						node[classList].toggle(classStr[i]);
					}
				}else{
					cls[condition ? "add" : "remove"](node, classStr);
				}
				return condition;   
			}
		}
	}
	*/

	
	var fakeNode = {};  
	cls = {
		contains: function containsClass(/*DomNode|String*/node, /*String*/classStr){
			return ((" " + dom.byId(node)[className] + " ").indexOf(" " + classStr + " ") >= 0); 
		},

		add: function addClass(/*DomNode|String*/node, /*String|Array*/classStr){
			node = dom.byId(node);
			classStr = str2array(classStr);
			var cls = node[className], oldLen;
			cls = cls ? " " + cls + " " : " ";
			oldLen = cls.length;
			for(var i = 0, len = classStr.length, c; i < len; ++i){
				c = classStr[i];
				if(c && cls.indexOf(" " + c + " ") < 0){
					cls += c + " ";
				}
			}
			if(oldLen < cls.length){
				node[className] = cls.substr(1, cls.length - 2);
			}
		},

		remove: function removeClass(/*DomNode|String*/node, /*String|Array?*/classStr){
			node = dom.byId(node);
			var cls;
			if(classStr !== undefined){
				classStr = str2array(classStr);
				cls = " " + node[className] + " ";
				for(var i = 0, len = classStr.length; i < len; ++i){
					cls = cls.replace(" " + classStr[i] + " ", " ");
				}
				cls = lang.trim(cls);
			}else{
				cls = "";
			}
			if(node[className] != cls){ node[className] = cls; }
		},

		replace: function replaceClass(/*DomNode|String*/node, /*String|Array*/addClassStr, /*String|Array?*/removeClassStr){
			node = dom.byId(node);
			fakeNode[className] = node[className];
			cls.remove(fakeNode, removeClassStr);
			cls.add(fakeNode, addClassStr);
			if(node[className] !== fakeNode[className]){
				node[className] = fakeNode[className];
			}
		},

		toggle: function toggleClass(/*DomNode|String*/node, /*String|Array*/classStr, /*Boolean?*/condition){
			node = dom.byId(node);
			if(condition === undefined){
				classStr = str2array(classStr);
				for(var i = 0, len = classStr.length, c; i < len; ++i){
					c = classStr[i];
					cls[cls.contains(node, c) ? "remove" : "add"](node, c);
				}
			}else{
				cls[condition ? "add" : "remove"](node, classStr);
			}
			return condition;   
		}
	};

	return cls;
});

},
'dojo/_base/window':function(){
define(["./kernel", "../has", "./sniff"], function(dojo, has){
	
	
	
	

/*=====
dojo.doc = {
	
	
	
	
	
	
	
	
	
}
=====*/
dojo.doc = this["document"] || null;

dojo.body = function(){
	
	
	
	
	

	
	
	return dojo.doc.body || dojo.doc.getElementsByTagName("body")[0]; 
};

dojo.setContext = function(/*Object*/globalObject, /*DocumentElement*/globalDocument){
	
	
	
	
	
	
	dojo.global = ret.global = globalObject;
	dojo.doc = ret.doc = globalDocument;
};

dojo.withGlobal = function(	/*Object*/globalObject,
							/*Function*/callback,
							/*Object?*/thisObject,
							/*Array?*/cbArguments){
	
	
	
	
	
	
	
	
	

	var oldGlob = dojo.global;
	try{
		dojo.global = ret.global = globalObject;
		return dojo.withDoc.call(null, globalObject.document, callback, thisObject, cbArguments);
	}finally{
		dojo.global = ret.global = oldGlob;
	}
};

dojo.withDoc = function(	/*DocumentElement*/documentObject,
							/*Function*/callback,
							/*Object?*/thisObject,
							/*Array?*/cbArguments){
	
	
	
	
	
	
	

	var oldDoc = dojo.doc,
		oldQ = dojo.isQuirks,
		oldIE = dojo.isIE, isIE, mode, pwin;

	try{
		dojo.doc = ret.doc = documentObject;
		
		dojo.isQuirks = has.add("quirks", dojo.doc.compatMode == "BackCompat", true, true); 

		if(has("ie")){
			if((pwin = documentObject.parentWindow) && pwin.navigator){
				
				
				
				isIE = parseFloat(pwin.navigator.appVersion.split("MSIE ")[1]) || undefined;
				mode = documentObject.documentMode;
				if(mode && mode != 5 && Math.floor(isIE) != mode){
					isIE = mode;
				}
				dojo.isIE = has.add("ie", isIE, true, true);
			}
		}

		if(thisObject && typeof callback == "string"){
			callback = thisObject[callback];
		}

		return callback.apply(thisObject, cbArguments || []);
	}finally{
		dojo.doc = ret.doc = oldDoc;
		dojo.isQuirks = has.add("quirks", oldQ, true, true);
		dojo.isIE = has.add("ie", oldIE, true, true);
	}
};

var ret = {
	global: dojo.global,
	doc: dojo.doc,
	body: dojo.body,
	setContext: dojo.setContext,
	withGlobal: dojo.withGlobal,
	withDoc: dojo.withDoc
};

return ret;

});

},
'dojo/_base/config':function(){
define(["../has", "require"], function(has, require){
	
	
	
	
	
	
    
    
	
	
	
	
	
	//		|	
	
	
	
	//		|			location:".", 
	
	
	
	//		|	
	
	//		|		
	
	
	
	//		|	
	
	//		|		
	//		|		
	

	var result = {};
	if(1){
		
		var src = require.rawConfig, p;
		for(p in src){
			result[p] = src[p];
		}
	}else{
		var adviseHas = function(featureSet, prefix, booting){
			for(p in featureSet){
				p!="has" && has.add(prefix + p, featureSet[p], 0, booting);
			}
		};
		result = 1 ?
			
			require.rawConfig :
			
			this.dojoConfig || this.djConfig || {};
		adviseHas(result, "config", 1);
		adviseHas(result.has, "", 1);
	}
	return result;

/*=====









dojoConfig = {
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	isDebug: false,
	
	
	//		specified according to [RFC 3066](http:
	
	
	
	
	
	locale: undefined,
	
	
	
	
	extraLocale: undefined,
	
	
	
	
	
	
	
	
	
	baseUrl: undefined,
	
	
	
	
	
	
	
	modulePaths: {},
	
	
	
	
	
	
	afterOnLoad: false,
	
	
	
	
	
	
	
	
	addOnLoad: null,
	
	
	
	require: [],
	
	
	
	defaultDuration: 200,
	
	
	
	
	
	
	
	dojoBlankHtmlUrl: undefined,
	
	
	
	
	ioPublish: false,
	
	
	
	
	useCustomLogger: undefined,
	
	
	
	transparentColor: undefined,
	
	
	
	
	
	
	
	
	skipIeDomLoaded: false
}
=====*/
});


},
'dojo/_base/event':function(){
define(["./kernel", "../on", "../has", "../dom-geometry"], function(dojo, on, has, dom){
  
  
  
  
	if(on._fixEvent){
		var fixEvent = on._fixEvent;
		on._fixEvent = function(evt, se){
			
			evt = fixEvent(evt, se);
			if(evt){
				dom.normalizeEvent(evt);
			}
			return evt;
		};		
	}
	dojo.fixEvent = function(/*Event*/ evt, /*DOMNode*/ sender){
		
		
		
		
		
		
		
		if(on._fixEvent){
			return on._fixEvent(evt, sender);
		}
		return evt;	
	};
	
	dojo.stopEvent = function(/*Event*/ evt){
		
		
		
		
		
		if(has("dom-addeventlistener") || (evt && evt.preventDefault)){
			evt.preventDefault();
			evt.stopPropagation();
		}else{
			evt = evt || window.event;
			evt.cancelBubble = true;
			on._preventDefault.call(evt);
		}
	};

	return {
		fix: dojo.fixEvent,
		stop: dojo.stopEvent
	};
});

},
'dojo/main':function(){
define([
	"./_base/kernel",
	"./has",
	"require",
	"./_base/sniff",
	"./_base/lang",
	"./_base/array",
	"./ready",
	"./_base/declare",
	"./_base/connect",
	"./_base/Deferred",
	"./_base/json",
	"./_base/Color",
	"./has!dojo-firebug?./_firebug/firebug",
	"./_base/browser",
	"./_base/loader"], function(dojo, has, require, sniff, lang, array, ready){
	
	
	
	

	
	
	
	
	
	if(dojo.config.isDebug){
		require(["./_firebug/firebug"]);
	}

	
	true || has.add("dojo-config-require", 1);
	if(1){
		var deps= dojo.config.require;
		if(deps){
			
			deps= array.map(lang.isArray(deps) ? deps : [deps], function(item){ return item.replace(/\./g, "/"); });
			if(dojo.isAsync){
				require(deps);
			}else{
				
				
				
				ready(1, function(){require(deps);});
			}
		}
	}

	return dojo;
});

},
'dojo/ready':function(){
define(["./_base/kernel", "./has", "require", "./domReady", "./_base/lang"], function(dojo, has, require, domReady, lang) {
	
	
	
	
	
	
	
	var
		
		isDomReady = 0,

		
		requestCompleteSignal,

		
		loadQ = [],

		
		onLoadRecursiveGuard = 0,

		handleDomReady = function(){
			isDomReady = 1;
			dojo._postLoad = dojo.config.afterOnLoad = true;
			if(loadQ.length){
				requestCompleteSignal(onLoad);
			}
		},

		
		onLoad = function(){
			if(isDomReady && !onLoadRecursiveGuard && loadQ.length){
				
				onLoadRecursiveGuard = 1;
				var f = loadQ.shift();
					try{
						f();
					}
						
					finally{
						onLoadRecursiveGuard = 0;
					}
				onLoadRecursiveGuard = 0;
				if(loadQ.length){
					requestCompleteSignal(onLoad);
				}
			}
		};

	
	if(1){
		require.on("idle", onLoad);
		requestCompleteSignal = function(){
			if(require.idle()){
				onLoad();
			} 
		};
	}else{
		
		requestCompleteSignal = function(){
			
			
			require.ready(onLoad);
		};
	}

	var ready = dojo.ready = dojo.addOnLoad = function(priority, context, callback){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	|		
		
		
		
		
		
		

		var hitchArgs = lang._toArray(arguments);
		if(typeof priority != "number"){
			callback = context;
			context = priority;
			priority = 1000;
		}else{
			hitchArgs.shift();
		}
		callback = callback ?
			lang.hitch.apply(dojo, hitchArgs) :
			function(){
				context();
			};
		callback.priority = priority;
		for(var i = 0; i < loadQ.length && priority >= loadQ[i].priority; i++){}
		loadQ.splice(i, 0, callback);
		requestCompleteSignal();
	};

	true || has.add("dojo-config-addOnLoad", 1);
	if(1){
		var dca = dojo.config.addOnLoad;
		if(dca){
			ready[(lang.isArray(dca) ? "apply" : "call")](dojo, dca);
		}
	}

	if(1 && dojo.config.parseOnLoad && !dojo.isAsync){
		ready(99, function(){
			if(!dojo.parser){
				dojo.deprecated("Add explicit require(['dojo/parser']);", "", "2.0");
				require(["dojo/parser"]);
			}
		});
	}

	if(1){
		domReady(handleDomReady);
	}else{
		handleDomReady();
	}

	return ready;
});

},
'dojo/aspect':function(){
define([], function(){




/*=====
	dojo.aspect = {
		
		
		
		
		
		
		
		
		
		
		
		//	|	signal.remove(); 
		
		//	|		
		
		
		after: function(target, methodName, advice, receiveArguments){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		},
		
		before: function(target, methodName, advice){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		},

		around: function(target, methodName, advice){
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			//		|			var results = originalFoo.apply(this, arguments); 
			
			
			
			
			
			
			
			
			
			
			
			
		}

	};
=====*/

	"use strict";
	function advise(dispatcher, type, advice, receiveArguments){
		var previous = dispatcher[type];
		var around = type == "around";
		var signal;
		if(around){
			var advised = advice(function(){
				return previous.advice(this, arguments);
			});
			signal = {
				remove: function(){
					signal.cancelled = true;
				},
				advice: function(target, args){
					return signal.cancelled ?
						previous.advice(target, args) : 
						advised.apply(target, args);	
				}
			};
		}else{
			
			signal = {
				remove: function(){
					var previous = signal.previous;
					var next = signal.next;
					if(!next && !previous){
						delete dispatcher[type];
					}else{
						if(previous){
							previous.next = next;
						}else{
							dispatcher[type] = next;
						}
						if(next){
							next.previous = previous;
						}
					}
				},
				advice: advice,
				receiveArguments: receiveArguments
			};
		}
		if(previous && !around){
			if(type == "after"){
				
				var next = previous;
				while(next){
					previous = next;
					next = next.next;
				}
				previous.next = signal;
				signal.previous = previous;
			}else if(type == "before"){
				
				dispatcher[type] = signal;
				signal.next = previous;
				previous.previous = signal;
			}
		}else{
			
			dispatcher[type] = signal;
		}
		return signal;
	}
	function aspect(type){
		return function(target, methodName, advice, receiveArguments){
			var existing = target[methodName], dispatcher;
			if(!existing || existing.target != target){
				
				dispatcher = target[methodName] = function(){
					
					var args = arguments;
					var before = dispatcher.before;
					while(before){
						args = before.advice.apply(this, args) || args;
						before = before.next;
					}
					
					if(dispatcher.around){
						var results = dispatcher.around.advice(this, args);
					}
					
					var after = dispatcher.after;
					while(after){
						results = after.receiveArguments ? after.advice.apply(this, args) || results :
								after.advice.call(this, results);
						after = after.next;
					}
					return results;
				};
				if(existing){
					dispatcher.around = {advice: function(target, args){
						return existing.apply(target, args);
					}};
				}
				dispatcher.target = target;
			}
			var results = advise((dispatcher || existing), type, advice, receiveArguments);
			advice = null;
			return results;
		};
	}
	return {
		before: aspect("before"),
		around: aspect("around"),
		after: aspect("after")
	};
});

},
'dojo/_base/connect':function(){
define(["./kernel", "../on", "../topic", "../aspect", "./event", "../mouse", "./sniff", "./lang", "../keys"], function(kernel, on, hub, aspect, eventModule, mouse, has, lang){










has.add("events-keypress-typed", function(){ 
	var testKeyEvent = {charCode: 0};
	try{
		testKeyEvent = document.createEvent("KeyboardEvent");
		(testKeyEvent.initKeyboardEvent || testKeyEvent.initKeyEvent).call(testKeyEvent, "keypress", true, true, null, false, false, false, false, 9, 3);
	}catch(e){}
	return testKeyEvent.charCode == 0 && !has("opera");
});

function connect_(obj, event, context, method, dontFix){
	method = lang.hitch(context, method);
	if(!obj || !(obj.addEventListener || obj.attachEvent)){
		
		
		return aspect.after(obj || kernel.global, event, method, true);
	}
	if(typeof event == "string" && event.substring(0, 2) == "on"){
		event = event.substring(2);
	}
	if(!obj){
		obj = kernel.global;
	}
	if(!dontFix){
		switch(event){
			
			case "keypress":
				event = keypress;
				break;
			case "mouseenter":
				event = mouse.enter;
				break;
			case "mouseleave":
				event = mouse.leave;
				break;
		}
	}
	return on(obj, event, method, dontFix);
}

var _punctMap = {
	106:42,
	111:47,
	186:59,
	187:43,
	188:44,
	189:45,
	190:46,
	191:47,
	192:96,
	219:91,
	220:92,
	221:93,
	222:39,
	229:113
};
var evtCopyKey = has("mac") ? "metaKey" : "ctrlKey";


var _synthesizeEvent = function(evt, props){
	var faux = lang.mixin({}, evt, props);
	setKeyChar(faux);
	
	
	
	faux.preventDefault = function(){ evt.preventDefault(); };
	faux.stopPropagation = function(){ evt.stopPropagation(); };
	return faux;
};
function setKeyChar(evt){
	evt.keyChar = evt.charCode ? String.fromCharCode(evt.charCode) : '';
	evt.charOrCode = evt.keyChar || evt.keyCode;
}
var keypress;
if(has("events-keypress-typed")){
	
	var _trySetKeyCode = function(e, code){
		try{
			
			
			return (e.keyCode = code);
		}catch(e){
			return 0;
		}
	};
	keypress = function(object, listener){
		var keydownSignal = on(object, "keydown", function(evt){
			
			var k=evt.keyCode;
			
			// http:
			var unprintable = (k!=13 || (has("ie") >= 9 && !has("quirks"))) && k!=32 && (k!=27||!has("ie")) && (k<48||k>90) && (k<96||k>111) && (k<186||k>192) && (k<219||k>222) && k!=229;
			
			if(unprintable||evt.ctrlKey){
				var c = unprintable ? 0 : k;
				if(evt.ctrlKey){
					if(k==3 || k==13){
						return listener.call(evt.currentTarget, evt); 
					}else if(c>95 && c<106){
						c -= 48; 
					}else if((!evt.shiftKey)&&(c>=65&&c<=90)){
						c += 32; 
					}else{
						c = _punctMap[c] || c; 
					}
				}
				
				var faux = _synthesizeEvent(evt, {type: 'keypress', faux: true, charCode: c});
				listener.call(evt.currentTarget, faux);
				if(has("ie")){
					_trySetKeyCode(evt, faux.keyCode);
				}
			}
		});
		var keypressSignal = on(object, "keypress", function(evt){
			var c = evt.charCode;
			c = c>=32 ? c : 0;
			evt = _synthesizeEvent(evt, {charCode: c, faux: true});
			return listener.call(this, evt);
		});
		return {
			remove: function(){
				keydownSignal.remove();
				keypressSignal.remove();
			}
		};
	};
}else{
	if(has("opera")){
		keypress = function(object, listener){
			return on(object, "keypress", function(evt){
				var c = evt.which;
				if(c==3){
					c=99; 
				}
				
				
				c = c<32 && !evt.shiftKey ? 0 : c;
				if(evt.ctrlKey && !evt.shiftKey && c>=65 && c<=90){
					
					c += 32;
				}
				return listener.call(this, _synthesizeEvent(evt, { charCode: c }));
			});
		};
	}else{
		keypress = function(object, listener){
			return on(object, "keypress", function(evt){
				setKeyChar(evt);
				return listener.call(this, evt);
			});
		};
	}
}

var connect = {
	_keypress:keypress,

	connect:function(obj, event, context, method, dontFix){
		
		var a=arguments, args=[], i=0;
		
		args.push(typeof a[0] == "string" ? null : a[i++], a[i++]);
		
		var a1 = a[i+1];
		args.push(typeof a1 == "string" || typeof a1 == "function" ? a[i++] : null, a[i++]);
		
		for(var l=a.length; i<l; i++){	args.push(a[i]); }
		return connect_.apply(this, args);
	},

	disconnect:function(handle){
		if(handle){
			handle.remove();
		}
	},

	subscribe:function(topic, context, method){
		return hub.subscribe(topic, lang.hitch(context, method));
	},

	publish:function(topic, args){
		return hub.publish.apply(hub, [topic].concat(args));
	},

	connectPublisher:function(topic, obj, event){
		var pf = function(){ connect.publish(topic, arguments); };
		return event ? connect.connect(obj, event, pf) : connect.connect(obj, pf); 
	},

	isCopyKey: function(e){
		return e[evtCopyKey];	
	}
};
connect.unsubscribe = connect.disconnect;

1 && lang.mixin(kernel, connect);
return connect;

/*=====
dojo.connect = function(obj, event, context, method, dontFix){
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//	|	dojo.connect(obj, "onchange", ui, ui.update); 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//	|	dojo.connect(ob, "onCustomEvent", "customEventHandler"); 
	
	
	
	
	
	//	|	dojo.connect(ob, "onCustomEvent", customEventHandler); 
	
	
	
	
	
	//	|	dojo.connect("globalEvent", globalHandler); 
}
=====*/

/*=====
dojo.disconnect = function(handle){
	
	
	
	
	
	
}
=====*/

/*=====
dojo.subscribe = function(topic, context, method){
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
=====*/

/*=====
dojo.unsubscribe = function(handle){
	
	
	
	
	
	
	
	
}
=====*/

/*=====
dojo.publish = function(topic, args){
	
	
	
	
	
	
	
	
	
	
}
=====*/

/*=====
dojo.connectPublisher = function(topic, obj, event){
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
=====*/

/*=====
dojo.isCopyKey = function(e){
	
	
	
	
}
=====*/

});



}}});

(function(){
	
	var require = this.require;
	
	require({cache:{}});
	!require.async && require(["dojo"]);
	require.boot && require.apply(null, require.boot);
})();