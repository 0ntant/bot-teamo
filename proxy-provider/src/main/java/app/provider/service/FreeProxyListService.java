package app.provider.service;

import app.provider.model.ProxyHost;
import app.provider.util.FileUtil;
import app.provider.util.GitUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
@Service
public class FreeProxyListService
{
    @Value("${free-proxy-list.dir}")
    String dirRep;

    @Value("${free-proxy-list.git-url}")
    String gitUrl;

    @Value("${free-proxy-list.socks5-dir}")
    String socks5Dir;

    @Value("${free-proxy-list.socks4-dir}")
    String socks4Dir;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ProxyValidateService proxyValidateServ;

    public void addProxyList()
    {
        updateProxyList();

        proxyValidateServ.addToActuatorList(getProxyHostsList(socks5Dir));
        proxyValidateServ.addToActuatorList(getProxyHostsList(socks4Dir));

        log.info("Remove dir {}", dirRep);
        FileUtil.removeNotEmptyDir(dirRep);
    }

    public Queue<ProxyHost> getProxyHostsList(String path)
    {
        File socks5File = new File(path);
        try
        {
            Queue<ProxyHost> proxyHosts
                    = new LinkedList<>(List.of(objectMapper.readValue(socks5File, ProxyHost[].class)));
            log.info("Proxy hosts size={} in file={}",
                    proxyHosts.size(),
                    path
            );

            return proxyHosts;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void updateProxyList()
    {
        if (GitUtil.isGitRepExists(dirRep))
        {
            pullRep();
        }
        else
        {
            cloneRep();
        }
    }

    private String cloneRep()
    {
        log.info("Clone repository={} into dir={}", gitUrl, dirRep);
        return GitUtil.cloneRepOneDepth(gitUrl, dirRep);
    }

    private void pullRep()
    {
        log.info("Pull repository={}", gitUrl);
        GitUtil.pullRep(dirRep);
    }
}
