app('cli', {
    id = 'app'
    hello('app')
})

log.info('脚本加载完成')

spec('cli', {
    id = 'cliA'
    hello('cli-a')
})

spec('cli', {
    id = 'cliB'
    hello('cli-b')
    include('/F:/Github/drm/drm-core/target/test-classes/scripts/a.groovy')
})

include('/F:/Github/drm/drm-core/target/test-classes/scripts/b.groovy')

cliA.hello('test hello a')

cliB.hello('test hello B')

app.hello('test hello app')