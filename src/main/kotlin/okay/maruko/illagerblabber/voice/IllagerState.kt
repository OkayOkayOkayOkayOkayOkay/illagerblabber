package okay.maruko.illagerblabber.voice


    sealed class IllagerState {
        //Idle, just vibing
        data object Passive : IllagerState()

        //Spotted the target
        data object Spotted : IllagerState()

        // In active combat
        data object Combat : IllagerState()

        // Taking Damage
        data object Hurt : IllagerState()

        //Just killed their target
        data object Victory : IllagerState()

    }
