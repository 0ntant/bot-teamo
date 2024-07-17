package app.provider.util;

import org.eclipse.jgit.api.Git;

import java.io.File;

public class GitUtil
{
    public static void pullRep(String repoDir)
    {
        try
        {
            Git git = Git.open( new File( repoDir + "/.git" ) );
            git.fetch().setRemote("origin").call();
            git.pull().call();
            git.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static String cloneRepOneDepth(String gitUrl, String dirToClone)
    {
        try(Git git =  Git.cloneRepository()
                .setURI(gitUrl)
                .setDirectory(new File(dirToClone))
                .setDepth(1)
                .call())
        {
            return git.getRepository().getDirectory().getAbsolutePath();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
    }
}
